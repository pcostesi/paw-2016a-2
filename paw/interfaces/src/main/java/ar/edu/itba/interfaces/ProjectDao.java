package ar.edu.itba.interfaces;

import ar.edu.itba.models.Project;

public interface ProjectDao {

	boolean projectExists(int projectId);

	boolean projectNameExists();

	Project createProject(String name, String description, String code);

	boolean projectCodeExists();

	boolean deleteProject(int projectId);

	boolean updateName(int projectId, String name);

	boolean updateDescription(int projectId, String description);

	boolean updateCode(int projectId, String code);

	Project getProjectById(int projectId);

}
