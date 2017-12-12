package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.ExperienceDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.service.ExperienceService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceDao experienceDao;

    @Autowired
    private ProjectDao projectDao;

    @Override
    public boolean addExperience(final Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (task.status().equals(Status.COMPLETED)) {
            final Project project = task.story().iteration().project();
            final Optional<User> taskOwner = task.owner();
            if (taskOwner.isPresent()) {
                final User user = taskOwner.get();
                int experience = 0;
                if (experienceDao.hasExperienceInProject(project, user)) {
                    experience = experienceDao.getExperience(project, user);
                } else {
                    experienceDao.createUserExperience(project, user);
                }
                experienceDao.setExperience(project, user, experience + task.score().getValue());
            } else {
                throw new IllegalStateException("Can't add experience to a task without ownership");
            }
        } else {
            throw new IllegalStateException("Can't add experience to an uncompleted task");
        }

        return true;
    }

    @Override
    public boolean removeExperience(final Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (!task.status().equals(Status.COMPLETED)) {
            final Project project = task.story().iteration().project();
            final Optional<User> taskOwner = task.owner();
            if (taskOwner.isPresent()) {
                final User user = taskOwner.get();
                if (experienceDao.hasExperienceInProject(project, user)) {
                    final int experience = experienceDao.getExperience(project, user);
                    final int taskScore = task.score().getValue();
                    if (experience - taskScore <= 0) {
                        experienceDao.deleteUserExperience(project, user);
                    } else {
                        experienceDao.setExperience(project, user, experience - task.score().getValue());
                    }
                } else {
                    throw new IllegalStateException("Can't remove experience from a user that has no experience");
                }
            } else {
                throw new IllegalStateException("Can't remove experience from a task without ownership");
            }
        } else {
            throw new IllegalStateException("Can't remove experience to an owner of a completed task");
        }

        return true;
    }

    @Override
    public int getTotalExperience(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }

        return experienceDao.getTotalExperience(user);
    }

    @Override
    public Map<User, Integer> getProjectExperience(final Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project can't be null");
        }

        final Map<User, Integer> projectExperience = new HashMap<User, Integer>();
        for (final User user : projectDao.getProjectMembers(project)) {
            projectExperience.put(user, experienceDao.getExperience(project, user));
        }

        return projectExperience;
    }

}