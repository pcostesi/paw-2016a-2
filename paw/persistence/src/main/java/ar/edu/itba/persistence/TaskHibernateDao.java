package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class TaskHibernateDao implements TaskDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Task> getTasksForStory(final Story story) {
        try {
            final TypedQuery<Task> query = em.createQuery("from Task task where task.story = :story order by task.priority desc", Task.class);
            query.setParameter("story", story);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get tasks for story");
        }
    }

    @Override
    @Transactional
    public boolean taskExists(final Task task) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.taskId = :taskId", Long.class);
            query.setParameter("taskId", task.taskId());
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check task exists");
        }
    }

    @Override
    @Transactional
    public boolean taskExists(final Story story, final String title) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.title = :title and task.story = :story", Long.class);
            query.setParameter("title", title);
            query.setParameter("story", story);
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check there is another task with such title in the story");
        }
    }

    @Override
    @Transactional
    public void updateStatus(final Task task, final Status status) {
        try {
            final Query query = em.createQuery("update Task set status = :status where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("status", status);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update status");
        }
    }

    @Override
    @Transactional
    public void updateOwner(final Task task, final User owner) {
        try {
            final Query query = em.createQuery("update Task set owner = :owner where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("owner", owner);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update owner");
        }
    }

    @Override
    @Transactional
    public void deleteTask(final Task task) {
        try {
            final Query query = em.createQuery("delete from Task where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete task");
        }
    }

    @Override
    @Transactional
    public Task getTaskById(final int taskId) {
        try {
            final TypedQuery<Task> query = em.createQuery("from Task task where task.taskId = :taskId", Task.class);
            query.setParameter("taskId", taskId);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get task by id");
        }
    }

    @Override
    @Transactional
    public Task createTask(final Story story, final String title, final String description, final Status status, final User user,
                           final Score score, final Priority priority) {
        try {
            final Task newTask = Task.builder()
                    .title(title)
                    .description(Optional.ofNullable(description))
                    .status(status)
                    .owner(Optional.ofNullable(user))
                    .score(score)
                    .priority(priority)
                    .story(story)
                    .build();
            em.persist(newTask);
            em.flush();
            return newTask;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to create task");
        }
    }

    @Override
    @Transactional
    public void updatePriority(final Task task, final Priority priority) {
        try {
            final Query query = em.createQuery("update Task set priority = :priority where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("priority", priority);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update priority");
        }
    }

    @Override
    @Transactional
    public void updateScore(final Task task, final Score score) {
        try {
            final Query query = em.createQuery("update Task set score = :score where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("score", score);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update task score");
        }
    }

    @Override
    @Transactional
    public Story getParent(final Task task) {
        try {
            return task.story();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get task parents");
        }
    }

    @Override
    @Transactional
    public void updateTitle(final Task task, final String title) {
        try {
            final Query query = em.createQuery("update Task set title = :title where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("title", title);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update task title");
        }
    }

    @Override
    @Transactional
    public void updateDescription(final Task task, final String description) {
        try {
            final Query query = em.createQuery("update Task set description = :description where taskId = :taskId");
            query.setParameter("taskId", task.taskId());
            query.setParameter("description", description);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update task description");
        }
    }

    @Override
    @Transactional
    public List<Task> getUnfinishedTasks(final Story oldStory) {
        try {
            final TypedQuery<Task> query = em.createQuery("from Task task where task.story = :story and (task.status = :notStarted or task.status = :started)", Task.class);
            query.setParameter("story", oldStory);
            query.setParameter("notStarted", Status.NOT_STARTED);
            query.setParameter("started", Status.STARTED);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get unfinished tasks");
        }
    }

    @Override
    @Transactional
    public void cloneTaskToStory(final Task task, final Story story) {
        try {
            final Task newTask = Task.builder()
                    .title(task.title())
                    .description(task.description())
                    .status(task.status())
                    .owner(task.owner())
                    .score(task.score())
                    .priority(task.priority())
                    .story(story)
                    .build();
            em.persist(newTask);
            em.flush();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to clone task ");
        }
    }

}
