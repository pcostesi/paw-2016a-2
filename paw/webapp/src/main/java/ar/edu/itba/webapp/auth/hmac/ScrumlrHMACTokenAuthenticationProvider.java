package ar.edu.itba.webapp.auth.hmac;

import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.auth.RestCredentials;
import ar.edu.itba.webapp.auth.RestToken;
import ar.edu.itba.webapp.auth.ScrumlrUserDetails;
import ar.edu.itba.webapp.auth.ScrumlrUserDetailsService;

@Component
public class ScrumlrHMACTokenAuthenticationProvider implements AuthenticationProvider {
    static Logger logger = LoggerFactory.getLogger(ScrumlrHMACTokenAuthenticationProvider.class);
    
	@Autowired
	protected UserService us;
	
	@Autowired
	protected ScrumlrUserDetailsService uds;
	
	public ScrumlrHMACTokenAuthenticationProvider() {}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final RestToken token = (RestToken) authentication; // safe cast. See `supports`
		
		final String apiKey = token.getPrincipal();
		final RestCredentials credentials = token.getCredentials();
		final String remoteSignature = credentials.getSignature();
		
		logger.debug("Got login request for user key {}, token {}", apiKey, remoteSignature);
		try {
			final User user = us.getByApiKey(apiKey);
			final String secret = user.password();
			final String signature = calculateHMAC(secret, credentials.getRequestData().toString());
			
			if (!signature.equals(remoteSignature)) {	
				logger.debug("Bad credentials for user {}. Remote {}, Local {}.",
						user.username(), remoteSignature, signature);
	            throw new BadCredentialsException("Invalid username or password, or clock skew detected.");
	        }
	
			final UserDetails details = uds.loadUserByUsername(user.username());
			logger.debug("Auth ok for user {}", user.username());
			final RestToken authorizedToken = new RestToken(apiKey, credentials, token.getTimestamp(), details.getAuthorities());
			authorizedToken.setDetails(details);
			return authorizedToken;
		} catch (IllegalStateException ex) {
			logger.debug("Bad credentials for apikey {}", apiKey, ex);
			throw new BadCredentialsException("This user doesn't exist or has been banned");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return RestToken.class.isAssignableFrom(authentication);
	}
	

    private String calculateHMAC(String secret, String data) {
        try {
        	return HmacSha256Signature.calculateRFC2104HMAC(data, secret);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }

}
