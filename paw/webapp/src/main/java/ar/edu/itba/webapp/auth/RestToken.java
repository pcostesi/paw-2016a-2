package ar.edu.itba.webapp.auth;

import java.time.Instant;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class RestToken extends UsernamePasswordAuthenticationToken {

	private Instant timestamp;
	
	public RestToken(String principal, RestCredentials credentials, Instant timestamp, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.timestamp = timestamp;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7727345799645282594L;
	
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
