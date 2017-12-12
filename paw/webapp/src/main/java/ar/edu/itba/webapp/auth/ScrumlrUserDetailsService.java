package ar.edu.itba.webapp.auth;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class ScrumlrUserDetailsService implements UserDetailsService {

    static private final Logger logger = LoggerFactory.getLogger(ScrumlrUserDetailsService.class);
    @Autowired
    private
    UserService us;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            logger.debug("Fetching user {}", username);
            final User user = us.getByUsername(username);
            final Collection<GrantedAuthority> authorities = new HashSet<>();

            authorities.add(new SimpleGrantedAuthority("USER"));
            return new ScrumlrUserDetails(user, true, true, true, true, authorities);
        } catch (final IllegalStateException e) {
            logger.debug("No user found for {}", username);
            throw new UsernameNotFoundException("No user found by the name " + username);
        }
    }

}
