package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Primary
@Repository
public class ProjectHibernateDao implements ProjectDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean projectExists(final Project project) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Project project where project = :project", Long.class);
            query.setParameter("project", project);
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check project exists");
        }
    }

    @Override
    @Transactional
    public boolean projectNameExists(final String name) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Project project where project.name = :name", Long.class);
            query.setParameter("name", name);
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check project name is free");
        }
    }

    @Override
    @Transactional
    public Project createProject(final User admin, final String title, final String description, final String code) {
        try {
            final Project persistableProject = Project.builder()
                    .name(title)
                    .description(description)
                    .code(code)
                    .startDate(LocalDate.now())
                    .admin(admin)
                    .build();
            em.persist(persistableProject);
            em.flush();
            return persistableProject;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to create project");
        }
    }

    @Override
    @Transactional
    public boolean projectCodeExists(final String code) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Project project where project.code = :code", Long.class);
            query.setParameter("code", code);
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check project code is used");
        }
    }

    @Override
    @Transactional
    public void deleteProject(final Project project) {
        try {
            final Query query = em.createQuery("delete from Project where projectId = :projectId");
            query.setParameter("projectId", project.projectId());
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete project");
        }
    }

    @Override
    @Transactional
    public void deleteProjectUser(final Project project) {
        try {
            project.getMembers().clear();
            em.persist(project);
            em.flush();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete projectuser");
        }
    }

    @Override
    @Transactional
    public void updateName(final Project project, final String name) {
        try {
            final Query query = em.createQuery("update Project set name = :name where projectId = :projectId");
            query.setParameter("projectId", project.projectId());
            query.setParameter("name", name);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update project name");
        }
    }

    @Override
    @Transactional
    public void updateDescription(final Project project, final String description) {
        try {
            final Query query = em.createQuery("update Project set description = :description where projectId = :projectId");
            query.setParameter("projectId", project.projectId());
            query.setParameter("description", description);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update project description");
        }
    }

    @Override
    @Transactional
    public void updateCode(final Project project, final String code) {
        try {
            final Query query = em.createQuery("update Project set code = :code where projectId = :projectId");
            query.setParameter("projectId", project.projectId());
            query.setParameter("code", code);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get update project code");
        }
    }

    @Override
    @Transactional
    public Project getProjectById(final int projectId) {
        try {
            final TypedQuery<Project> query = em.createQuery("from Project project where project.projectId = :projectId", Project.class);
            query.setParameter("projectId", projectId);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get project by id");
        }
    }

    @Override
    @Transactional
    public List<Project> getProjectsForUser(final User user) {
        try {
            final TypedQuery<Project> query = em.createQuery("select p from Project p join p.members u where u = :user", Project.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get projects list");
        }
    }

    @Override
    @Transactional
    public Project getProjectByCode(final String code) {
        try {
            final TypedQuery<Project> query = em.createQuery("from Project project where project.code = :code", Project.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get project by code");
        }
    }

    @Override
    @Transactional
    public void addProjectMember(final Project project, final User user) {
        try {
        	final Project persistedProject = getProjectByCode(project.code());
        	persistedProject.getMembers().add(user);
            em.persist(persistedProject);
            em.flush();
        } catch (final Exception exception) {
        	throw new IllegalStateException("Database failed to add project member");
        }
    }

    @Override
    @Transactional
    public List<User> getProjectMembers(final Project project) {
        try {
            final TypedQuery<User> query = em.createQuery("select u from Project p join p.members u where p = :project", User.class);
            query.setParameter("project", project);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get project members");
        }
    }

    @Override
    @Transactional
    public void deleteProjectMember(final Project project, final User userToDelete) {
        try {
        	final Project persistedProject = getProjectByCode(project.code());
        	persistedProject.getMembers().remove(userToDelete);
            em.persist(persistedProject);
            em.flush();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete user from project");
        }
    }

    @Override
    @Transactional
    public boolean userBelongsToProject(final Project project, final User user) {
        try {
            return project.getMembers().contains(user);
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check user belongs to project");
        }
    }

}
