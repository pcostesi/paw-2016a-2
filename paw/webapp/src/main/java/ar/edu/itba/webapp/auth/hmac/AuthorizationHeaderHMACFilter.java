package ar.edu.itba.webapp.auth.hmac;

import ar.edu.itba.webapp.auth.RestCredentials;
import ar.edu.itba.webapp.auth.RestToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationHeaderHMACFilter extends OncePerRequestFilter {

    private static final Pattern AUTHORIZATION_HEADER_PATTERN = Pattern.compile("^HMAC (\\S+):([\\S]+)$");
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationHeaderHMACFilter.class);

    private final AuthenticationManager am;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public AuthorizationHeaderHMACFilter(final AuthenticationManager am, final AuthenticationEntryPoint ae) {
        this.am = am;
        this.authenticationEntryPoint = ae;
    }

    private String digestBody(final HttpServletRequest request) {
        return "test";
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
     throws ServletException, IOException {
        logger.debug("Attempting Authorization Header HMAC login");

        if (request.getMethod().equals(HttpMethod.OPTIONS)) {
            logger.debug("Is OPTIONS");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String dateHeader = request.getHeader(HttpHeaders.DATE);

        if (authHeader == null) {
            logger.debug("Request missing auth (null)");
            filterChain.doFilter(request, response);
            return;
        }

        final Matcher authHeaderMatcher = AUTHORIZATION_HEADER_PATTERN.matcher(authHeader);

        if (!authHeaderMatcher.matches()) {
            logger.debug("Auth header does not match our signature");
            // no auth header, continue with the next filter and return.
            filterChain.doFilter(request, response);
            return;
        }

        final String apiKey = authHeaderMatcher.group(1);
        final String signature = authHeaderMatcher.group(2);
        final Instant date = dateHeader == null ? Instant.now() : Instant.parse(dateHeader);

        logger.info("Got auth request for key {} signature {}", apiKey, signature);
        final ScrumlrHMACRequestData data = ScrumlrHMACRequestData.builder()
         .date(date)
         .method(request.getMethod())
         .bodyDigest(digestBody(request))
         .build();

        final RestCredentials credentials = new RestCredentials(data, signature);

        final Authentication authentication = new RestToken(apiKey, credentials, date, null);

        try {
            final Authentication successfulAuth = am.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(successfulAuth);
            logger.debug("User auth ok {}", successfulAuth);
            filterChain.doFilter(request, response);
        } catch (final AuthenticationException authenticationException) {
            // If it fails clear this threads context and kick off the
            // authentication entry point process.
            SecurityContextHolder.clearContext();

            authenticationEntryPoint.commence(request, response, authenticationException);
        }

    }


}
