package ar.edu.itba.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Component
public class ScrumlrAuthenticationProvider implements AuthenticationProvider {
    static Logger logger = LoggerFactory.getLogger(ScrumlrAuthenticationProvider.class);

	@Autowired
	private UserService us;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = (String) authentication.getPrincipal();
		final String password = (String) authentication.getCredentials();
		
		try {
			final User user = us.getByUsername(username);
			
			logger.debug("Performing authentication for user {}", username);
			if (user.getPassword().equals(password)) {
				final Collection<GrantedAuthority> authorities = new HashSet<>();
				authorities.add(new SimpleGrantedAuthority("USER"));
	
				logger.debug("User authenticated as " + user.getUsername());
				return new UsernamePasswordAuthenticationToken(username, password, authorities);
			}
		
		} catch (IllegalStateException e) {}
		throw new UsernameNotFoundException("No user found by the name " + username);
	}

	@Override
	public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
