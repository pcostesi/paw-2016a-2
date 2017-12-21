package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

import java.util.List;

public interface UserDao {

    User getByUsername(final String username);

    boolean userNameExists(final String name);

    boolean userMailExists(final String mail);

    User createUser(final String name, final String password, final String mail);

    List<String> getAllUsernames();

    List<String> getAllUsernamesExcept(final User user);

    void setPassword(final User user, final String newPassword);

    List<String> getAllUsernamesOfProject(final Project project);

    List<String> getAvailableUsers(final Project project);
    
    boolean deleteUserByUsername(final String username);

}