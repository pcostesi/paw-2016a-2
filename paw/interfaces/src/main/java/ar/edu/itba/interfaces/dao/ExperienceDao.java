package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface ExperienceDao {

    boolean setExperience(final Project project, final User user, int experience);

    int getExperience(final Project project, final User user);

    boolean hasExperienceInProject(final Project project, final User user);

    boolean createUserExperience(final Project project, final User user);

    boolean deleteUserExperience(final Project project, final User user);

    int getTotalExperience(final User user);

}
