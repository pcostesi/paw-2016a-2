package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface ExperienceDao {

	public boolean setExperience(final Project project, final User user, int experience);

	public int getExperience(final Project project, final User user);

	public boolean hasExperienceInProject(final Project project, final User user);

	public boolean createUserExperience(final Project project, final User user);

	public boolean deleteUserExperience(final Project project, final User user);

	public int getTotalExperience(final User user);

}
