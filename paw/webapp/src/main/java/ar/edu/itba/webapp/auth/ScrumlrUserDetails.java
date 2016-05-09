package ar.edu.itba.webapp.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import ar.edu.itba.models.User;

public class ScrumlrUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 7432253838250082238L;
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public ScrumlrUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.username(), user.password(), authorities);
		this.user = user;
	}

	public ScrumlrUserDetails(User user, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.username(), user.password(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

}
