package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

import java.util.List;

public interface ProjectDao {

    boolean projectExists(final Project project);

    boolean projectNameExists(final String name);

    Project createProject(final User admin, final String name, final String description, final String code);

    boolean projectCodeExists(String code);

    void deleteProject(final Project project);

    void updateName(final Project project, final String name);

    void updateDescription(final Project project, final String description);

    void updateCode(final Project project, final String code);

    Project getProjectById(final int projectId);

    List<Project> getProjectsForUser(final User user);

    Project getProjectByCode(final String code);

    void addProjectMember(final Project project, final User user);

    List<User> getProjectMembers(final Project project);

    void deleteProjectUser(final Project project);

    void deleteProjectMember(final Project project, final User userToDelete);

    boolean userBelongsToProject(final Project project, final User user);

}
