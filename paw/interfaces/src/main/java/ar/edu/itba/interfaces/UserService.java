package ar.edu.itba.interfaces;

import ar.edu.itba.models.User;

public interface UserService {
	
	public User create(final String name, final String password, final String mail);
	
    public User getByUsername(final String username);
  
}
