package ar.edu.itba.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Component
public class ScrumlrUserDetailsService implements UserDetailsService {

	@Autowired
	UserService us;
	
	static private final Logger logger = LoggerFactory.getLogger(ScrumlrUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = us.getByUsername(username);
		
		logger.debug("Fetching user " + username);
		if (user != null) {
			final Collection<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(), authorities);
		}

		logger.debug("No user " + username);
		return null;
	}

}
