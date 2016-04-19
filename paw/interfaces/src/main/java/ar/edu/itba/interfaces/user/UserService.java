package ar.edu.itba.interfaces.user;

import ar.edu.itba.models.user.User;

public interface UserService {
	
	public User create(String name, String password, String mail);
	
    /* Fetch a user by username
    * @param username The user's name.
    * @return The matched user, or null if none matches.
    */
   public User getByUsername(String username);
  
}
