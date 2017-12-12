package ar.edu.itba.webapp.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;

@SuppressWarnings("serial")
public class StaticLocaleAwareServlet extends HttpServlet {

    private static final int deflateThreshold = 4 * 1024;
    private static final int bufferSize = 4 * 1024;
    private final static Logger logger = LoggerFactory.getLogger(StaticLocaleAwareServlet.class);

    public StaticLocaleAwareServlet() {
        super();
        logger.info("Static File Server is up and running");
    }

    private static boolean acceptsDeflate(final HttpServletRequest req) {
        final String ae = req.getHeader("Accept-Encoding");
        return ae != null && ae.contains("gzip");
    }

    private static boolean deflatable(final String mimetype) {
        return mimetype.startsWith("text/")
         || "application/postscript".equals(mimetype)
         || mimetype.startsWith("application/ms")
         || mimetype.startsWith("application/vnd")
         || mimetype.endsWith("xml");
    }

    private static void transferStreams(final InputStream is, final OutputStream os) throws IOException {
        try {
            final byte[] buf = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        lookup(req).respondGet(resp);
    }

    @Override
    protected void doHead(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        try {
            lookup(req).respondHead(resp);
        } catch (final UnsupportedOperationException e) {
            super.doHead(req, resp);
        }
    }

    @Override
    protected long getLastModified(final HttpServletRequest req) {
        return lookup(req).getLastModified();
    }

    private LookupResult lookup(final HttpServletRequest req) {
        LookupResult r = (LookupResult) req.getAttribute("lookupResult");
        if (r == null) {
            r = lookupNoCache(req);
            req.setAttribute("lookupResult", r);
        }
        return r;
    }

    private LookupResult lookupNoCache(final HttpServletRequest req) {
        logger.debug("Got {} for {}", req.getMethod(), req.getServletPath());
        final String[] paths = getLocalizedPaths(req);
        for (final String path : paths) {
            if (isForbidden(path)) {
                return new LookupResultError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            }
        }

        final LookupResult notFound = new LookupResultError(HttpServletResponse.SC_NOT_FOUND, "Not Found D:");
        LookupResult defaultResponse = notFound;
        final String defaultPath = getServletConfig().getInitParameter("default-file");
        if (defaultPath != null) {
            final List<String> prefixes = getLocalizationPrefixes(req);
            defaultResponse = tryFiles(req, toLocalizedPaths(prefixes, defaultPath)).orElse(notFound);
        }

        return tryFiles(req, paths)
         .orElse(defaultResponse);
    }

    private List<String> getLocalizationPrefixes(final HttpServletRequest req) {
        final String DEFAULT_LOCALE_FOLDER = getServletConfig().getInitParameter("default-locale-folder");
        final List<String> options = new ArrayList<>();
        for (final Locale locale : getPreferredLanguages(req)) {
            final String tag = locale.toString();
            options.add(tag);
            final int idx = tag.indexOf('-');
            if (idx > 0) {
                options.add(tag.substring(0, idx));
            }
        }
        options.add(DEFAULT_LOCALE_FOLDER);
        return options;
    }

    private String[] toLocalizedPaths(final List<String> prefixes, final String path) {
        return prefixes.stream()
         .distinct()
         .map(localePrefix -> "/" + localePrefix + path)
         .toArray(String[]::new);
    }

    private String[] getLocalizedPaths(final HttpServletRequest req) {
        final List<String> options = getLocalizationPrefixes(req);
        logger.debug("locales are: {}", options);
        final String servletPath = "/".equals(req.getServletPath()) ? "/index.html" : req.getServletPath();
        final String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "";
        return toLocalizedPaths(options, servletPath + pathInfo);
    }

    private List<Locale> getLocalesFromHeader(final HttpServletRequest req) {
        final String requestedLangs = req.getHeader("Accept-Language");
        return Locale.LanguageRange.parse(requestedLangs).stream()
         .map(range -> new Locale(range.getRange()))
         .collect(Collectors.toList());
    }

    private List<Locale> getPreferredLanguages(final HttpServletRequest req) {
        final String LOCALE_COOKIE = getServletConfig().getInitParameter("locale-cookie");
        final List<Locale> locales = new ArrayList<>();
        if (req.getCookies() == null) {
            return getLocalesFromHeader(req);
        }
        final Optional<Locale> localeIndicator = Arrays.stream(req.getCookies())
         .filter(cookie -> LOCALE_COOKIE.equalsIgnoreCase(cookie.getName()))
         .map(cookie -> Locale.forLanguageTag(cookie.getValue()))
         .filter(Objects::nonNull)
         .findFirst();
        localeIndicator.ifPresent(locale -> {
            logger.debug("locale from cookie is {}", locale);
            locales.add(locale);
        });
        locales.addAll(getLocalesFromHeader(req));
        return locales;
    }

    private LookupResult fetchFile(final String realpath, final URL url, final String mimeType, final boolean shouldDeflate) {
        final File f = new File(realpath);
        if (!f.isFile()) {
            logger.debug("Forbidden to {}", realpath);
            return new LookupResultError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
        logger.debug("Found at {}", realpath);
        return new StaticFile(f.lastModified(), mimeType, (int) f.length(), shouldDeflate, url);
    }

    private LookupResult fetchFromJar(final URL url, final String mimeType, final boolean shouldDeflate) {
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
        } catch (final ClassCastException e) {
            return new StaticFile(-1, mimeType, -1, shouldDeflate, url);
        } catch (final IOException e) {
            return null;
        }
        return new StaticFile(-1, mimeType, -1, shouldDeflate, url);
    }

    private Optional<LookupResult> tryFiles(final HttpServletRequest req, final String... paths) {
        return Arrays.stream(paths)
         .map((String path) -> tryFile(req, path))
         .filter(Objects::nonNull)
         .findFirst();
    }

    private LookupResult tryFile(final HttpServletRequest req, final String path) {
        final URL url;
        final String RESOURCES_PREFIX = getServletConfig().getInitParameter("assets-route");
        try {
            url = getServletContext().getResource(RESOURCES_PREFIX + path);
        } catch (final MalformedURLException e) {
            return null;
        }
        if (url == null) {
            return null;
        }

        final String mimeType = getMimeType(path);
        final boolean shouldDeflate = acceptsDeflate(req);
        final String realpath = getServletContext().getRealPath(RESOURCES_PREFIX + path);

        if (realpath != null) {
            logger.debug("Found using filesystem: {}", realpath);
            return fetchFile(realpath, url, mimeType, shouldDeflate);
        } else {
            logger.debug("Trying with jar resource: {}", url);
            return fetchFromJar(url, mimeType, shouldDeflate);
        }
    }

    private boolean isForbidden(final String path) {
        final String lpath = path.toLowerCase();
        return lpath.startsWith("/web-inf/") || lpath.startsWith("/meta-inf/");
    }

    private String getMimeType(final String path) {
        final String mimeType = getServletContext().getMimeType(path);
        return mimeType != null ? mimeType : "application/octet-stream";
    }

    public interface LookupResult {
        void respondGet(HttpServletResponse resp) throws IOException;

        void respondHead(HttpServletResponse resp);

        long getLastModified();
    }

    public static class LookupResultError implements LookupResult {
        final int statusCode;
        final String message;

        public LookupResultError(final int statusCode, final String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public long getLastModified() {
            return -1;
        }

        public void respondGet(final HttpServletResponse resp) throws IOException {
            resp.sendError(statusCode, message);
        }

        public void respondHead(final HttpServletResponse resp) {
            throw new UnsupportedOperationException();
        }
    }

    public static class StaticFile implements LookupResult {
        final long lastModified;
        final String mimeType;
        final int contentLength;
        final boolean acceptsDeflate;
        final URL url;

        public StaticFile(final long lastModified, final String mimeType, final int contentLength, final boolean acceptsDeflate, final URL url) {
            this.lastModified = lastModified;
            this.mimeType = mimeType;
            this.contentLength = contentLength;
            this.acceptsDeflate = acceptsDeflate;
            this.url = url;
        }

        public long getLastModified() {
            return lastModified;
        }

        boolean willDeflate() {
            return acceptsDeflate && deflatable(mimeType) && contentLength >= deflateThreshold;
        }

        void setHeaders(final HttpServletResponse resp) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(mimeType);
            if (contentLength >= 0 && !willDeflate()) {
                resp.setContentLength(contentLength);
            }
        }

        public void respondGet(final HttpServletResponse resp) throws IOException {
            setHeaders(resp);
            final OutputStream os;
            if (willDeflate()) {
                resp.setHeader("Content-Encoding", "gzip");
                os = new GZIPOutputStream(resp.getOutputStream(), bufferSize);
            } else {
                os = resp.getOutputStream();
            }
            transferStreams(url.openStream(), os);
        }

        public void respondHead(final HttpServletResponse resp) {
            if (willDeflate()) {
                throw new UnsupportedOperationException();
            }
            setHeaders(resp);
        }
    }
}
