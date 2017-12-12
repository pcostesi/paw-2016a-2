package ar.edu.itba.webapp.auth;

import ar.edu.itba.models.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ScrumlrUserDetails extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 7432253838250082238L;
    private final User user;

    public ScrumlrUserDetails(final User user, final Collection<? extends GrantedAuthority> authorities) {
        super(user.username(), user.password(), authorities);
        this.user = user;
    }

    public ScrumlrUserDetails(final User user, final boolean enabled, final boolean accountNonExpired,
                              final boolean credentialsNonExpired, final boolean accountNonLocked,
                              final Collection<? extends GrantedAuthority> authorities) {
        super(user.username(), user.password(), enabled, accountNonExpired,
         credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
