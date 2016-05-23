package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.User;

public interface UserDao {
    
    public User getByUsername(final String username);

	public boolean userNameExists(final String name);

	public boolean userMailExists(final String mail);

	public User createUser(final String name, final String password, final String mail);

	public List<String> getAllUsernames();

	public List<String> getAllUsernamesExcept(final User user);

	void setPassword(User user, String newPassword);
    
}