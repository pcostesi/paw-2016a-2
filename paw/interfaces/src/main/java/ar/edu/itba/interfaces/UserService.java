package ar.edu.itba.interfaces;

import ar.edu.itba.models.User;

public interface UserService {
	
	public User create(String name, String password, String mail);
	
    /* Fetch a user by username
    * @param username The user's name.
    * @return The matched user, or null if none matches.
    */
   public User getByUsername(String username);
  
}
