package ar.edu.itba.webapp.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;


public class RestToken extends UsernamePasswordAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 7727345799645282594L;
    private final Instant timestamp;

    public RestToken(final String principal, final RestCredentials credentials, final Instant timestamp, final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.timestamp = timestamp;
    }

    @Override
    public String getPrincipal() {
        return (String) super.getPrincipal();
    }

    @Override
    public RestCredentials getCredentials() {
        return (RestCredentials) super.getCredentials();
    }

    public Instant getTimestamp() {
        return timestamp;
    }


}
