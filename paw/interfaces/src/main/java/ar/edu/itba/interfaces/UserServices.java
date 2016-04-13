package ar.edu.itba.interfaces;

import ar.edu.itba.models.User;

public interface UserServices {
	
	public User create(String name, String password);
	
    /* Fetch a user by username
    * @param username The user's name.
    * @return The matched user, or null if none matches.
    */
   User getByUsername(String username);
}
