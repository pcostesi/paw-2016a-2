package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.User;

public interface UserService {
	
	public User create(final String name, final String password, final String mail);
	
    public User getByUsername(final String username);
    
    public List<String> getUsernames();

	public boolean usernameExists(final String user);

	public void editPassword(String username, String newPassword);

	public boolean emailExists(final String user);

	public List<String> getUsernamesExcept(final User user);
  
}
