package ar.edu.itba.webapp.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class StaticLocaleAwareServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(StaticLocaleAwareServlet.class);
    protected static final int deflateThreshold = 4 * 1024;
    protected static final int bufferSize = 4 * 1024;

    protected static boolean acceptsDeflate(HttpServletRequest req) {
        final String ae = req.getHeader("Accept-Encoding");
        return ae != null && ae.contains("gzip");
    }

    protected static boolean deflatable(String mimetype) {
        return mimetype.startsWith("text/")
                || mimetype.equals("application/postscript")
                || mimetype.startsWith("application/ms")
                || mimetype.startsWith("application/vnd")
                || mimetype.endsWith("xml");
    }

    protected static void transferStreams(InputStream is, OutputStream os) throws IOException {
        try {
            byte[] buf = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1)
                os.write(buf, 0, bytesRead);
        } finally {
            is.close();
            os.close();
        }
    }

    public StaticLocaleAwareServlet() {
        super();
        logger.info("Static File Server is up and running");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Got GET");
        lookup(req).respondGet(resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            lookup(req).respondHead(resp);
        } catch(UnsupportedOperationException e) {
            super.doHead(req, resp);
        }
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return lookup(req).getLastModified();
    }

    protected LookupResult lookup(HttpServletRequest req) {
        LookupResult r = (LookupResult)req.getAttribute("lookupResult");
        if(r == null) {
            r = lookupNoCache(req);
            req.setAttribute("lookupResult", r);
        }
        return r;
    }

    protected LookupResult lookupNoCache(HttpServletRequest req) {
        String[] paths = getLocalizedPaths(req);
        for (String path : paths) {
            if (isForbidden(path)) {
                return new LookupResultError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            }
        }

        return tryFiles(req, paths);
    }

    protected String[] getLocalizedPaths(HttpServletRequest req) {
        final String DEFAULT_LOCALE_FOLDER = getServletConfig().getInitParameter("default-locale-folder");
        List<String> options = new ArrayList<>();
        for (Locale locale : getPreferredLanguages(req)) {
            String tag = locale.toString();
            options.add(tag);
            int idx = tag.indexOf('-');
            if (idx > 0) {
                options.add(tag.substring(0, idx));
            }
        }
        options.add(DEFAULT_LOCALE_FOLDER);
        logger.debug("locales are: {}", options);
        String servletPath = req.getServletPath().equals("/") ? "/index.html" : req.getServletPath();
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "";
        return options.stream()
                .distinct()
                .map(localePrefix ->  "/" + localePrefix + servletPath + pathInfo)
                .toArray(String[]::new);
    }

    protected List<Locale> getLocalesFromHeader(HttpServletRequest req) {
        String requestedLangs = req.getHeader("Accept-Language");
        return Locale.LanguageRange.parse(requestedLangs).stream()
                .map(range -> new Locale(range.getRange()))
                .collect(Collectors.toList());
    }

    protected List<Locale> getPreferredLanguages(HttpServletRequest req) {
        final String LOCALE_COOKIE = getServletConfig().getInitParameter("locale-cookie");
        List<Locale> locales = new ArrayList<>();
        if (req.getCookies() == null) {
            return getLocalesFromHeader(req);
        }
        Optional<Locale> localeIndicator = Arrays.stream(req.getCookies())
                .filter(cookie -> LOCALE_COOKIE.equals(cookie.getName()))
                .map(cookie -> Locale.forLanguageTag(cookie.getValue()))
                .filter(elem -> elem != null)
                .findFirst();
        if (localeIndicator.isPresent()) {
            locales.add(localeIndicator.get());
        }
        locales.addAll(getLocalesFromHeader(req));
        return locales;
    }

    protected LookupResult fetchFile(String realpath, URL url, String mimeType, boolean shouldDeflate) {
            File f = new File(realpath);
            if(!f.isFile()) {
                logger.debug("Forbidden to {}", realpath);
                return new LookupResultError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            }
            logger.debug("Found at {}", realpath);
            return new StaticFile(f.lastModified(), mimeType, (int) f.length(), shouldDeflate, url);
    }

    protected LookupResult fetchFromJar(URL url, String mimeType, boolean shouldDeflate) {
        try {
            // Try as a JAR Entry
            final ZipEntry ze = ((JarURLConnection) url.openConnection()).getJarEntry();
            if (ze != null) {
                if (ze.isDirectory()) {
                    return new LookupResultError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                } else {
                    return new StaticFile(ze.getTime(), mimeType, (int) ze.getSize(), shouldDeflate, url);
                }
            }
        } catch(ClassCastException e) {
            return new StaticFile(-1, mimeType, -1, shouldDeflate, url);
        } catch (IOException e) {
            return null;
        }
        return new StaticFile(-1, mimeType, -1, shouldDeflate, url);
    }

    protected LookupResult tryFiles(HttpServletRequest req, final String ...paths) {
        LookupResult notFound = new LookupResultError(HttpServletResponse.SC_NOT_FOUND, "Not Found D:");
        return Arrays.stream(paths)
                .map((String path) -> tryFile(req, path))
                .filter(file -> file != null)
                .findFirst()
                .orElse(notFound);
    }

    protected LookupResult tryFile(HttpServletRequest req, final String path) {
        final URL url;
        final String RESOURCES_PREFIX = getServletConfig().getInitParameter("assets-route");
        try {
            url = getServletContext().getResource(RESOURCES_PREFIX + path);
        } catch(MalformedURLException e) {
            return null;
        }
        if(url == null) {
            return null;
        }

        final String mimeType = getMimeType(path);
        final boolean shouldDeflate = acceptsDeflate(req);
        final String realpath = getServletContext().getRealPath(RESOURCES_PREFIX + path);

        if(realpath != null) {
            logger.debug("Found using filesystem: {}", realpath);
            return fetchFile(realpath, url, mimeType, shouldDeflate);
        } else {
            logger.debug("Trying with jar resource: {}", url);
            return fetchFromJar(url, mimeType, shouldDeflate);
        }
    }

    protected boolean isForbidden(String path) {
        String lpath = path.toLowerCase();
        return lpath.startsWith("/web-inf/") || lpath.startsWith("/meta-inf/");
    }

    protected String getMimeType(String path) {
        final String mimeType = getServletContext().getMimeType(path);
        return mimeType != null ? mimeType : "application/octet-stream";
    }

    public interface LookupResult {
        void respondGet(HttpServletResponse resp) throws IOException;
        void respondHead(HttpServletResponse resp);
        long getLastModified();
    }

    public static class LookupResultError implements LookupResult {
        protected final int statusCode;
        protected final String message;

        public LookupResultError(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public long getLastModified() {
            return -1;
        }

        public void respondGet(HttpServletResponse resp) throws IOException {
            resp.sendError(statusCode,message);
        }

        public void respondHead(HttpServletResponse resp) {
            throw new UnsupportedOperationException();
        }
    }

    public static class StaticFile implements LookupResult {
        protected final long lastModified;
        protected final String mimeType;
        protected final int contentLength;
        protected final boolean acceptsDeflate;
        protected final URL url;

        public StaticFile(long lastModified, String mimeType, int contentLength, boolean acceptsDeflate, URL url) {
            this.lastModified = lastModified;
            this.mimeType = mimeType;
            this.contentLength = contentLength;
            this.acceptsDeflate = acceptsDeflate;
            this.url = url;
        }

        public long getLastModified() {
            return lastModified;
        }

        protected boolean willDeflate() {
            return acceptsDeflate && deflatable(mimeType) && contentLength >= deflateThreshold;
        }

        protected void setHeaders(HttpServletResponse resp) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(mimeType);
            if(contentLength >= 0 && !willDeflate())
                resp.setContentLength(contentLength);
        }

        public void respondGet(HttpServletResponse resp) throws IOException {
            setHeaders(resp);
            final OutputStream os;
            if(willDeflate()) {
                resp.setHeader("Content-Encoding", "gzip");
                os = new GZIPOutputStream(resp.getOutputStream(), bufferSize);
            } else
                os = resp.getOutputStream();
            transferStreams(url.openStream(),os);
        }

        public void respondHead(HttpServletResponse resp) {
            if(willDeflate())
                throw new UnsupportedOperationException();
            setHeaders(resp);
        }
    }
}
