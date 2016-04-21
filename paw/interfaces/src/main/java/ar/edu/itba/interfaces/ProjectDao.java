package ar.edu.itba.interfaces;

import ar.edu.itba.models.Project;

public interface ProjectDao {

	boolean projectExists(final int projectId);

	boolean projectNameExists(final String name);

	Project createProject(final String name, final String description, final String code);

	boolean projectCodeExists(String code);

	boolean deleteProject(final int projectId);

	boolean updateName(final int projectId, final String name);

	boolean updateDescription(final int projectId, final String description);

	boolean updateCode(final int projectId, final String code);

	Project getProjectById(final int projectId);

}
