package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

@Primary
@Repository
public class TaskHibernateDao implements TaskDao{

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public List<Task> getTasksForStory(Story story) {
		try {
			final TypedQuery<Task> query = em.createQuery("from Task task where task.story = :story", Task.class);
			query.setParameter("story", story);
			return query.getResultList();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get tasks for story");
		}	
	}

	@Override
	@Transactional
	public boolean taskExists(Task task) {
		try{
			final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.taskId = :taskId", Long.class);
			query.setParameter("taskId", task.taskId());
			return query.getSingleResult() > 0;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check task exists");
		}	
	}

	@Override
	@Transactional
	public boolean taskExists(Story story, String title) {
		try{
			final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.title = :title and task.story = :story", Long.class);
			query.setParameter("title", title);
			query.setParameter("story", story);
			return query.getSingleResult() > 0;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check there is another task with such title in the story");
		}	
	}

	@Override
	@Transactional
	public void updateStatus(Task task, Status status) {
		try{
			final Query query = em.createQuery("update Task set status = :status where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("status", status);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update status");
		}	
	}

	@Override
	@Transactional
	public void updateOwner(Task task, User owner) {
		try{
			final Query query = em.createQuery("update Task set owner = :owner where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("owner", owner);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update owner");
		}	
	}

	@Override
	@Transactional
	public void deleteTask(Task task) {
		try{
			final Query query = em.createQuery("delete from Task where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to delete task");
		}	
	}

	@Override
	@Transactional
	public Task getTaskById(int taskId) {
		try{
			final TypedQuery<Task> query = em.createQuery("from Task task where task.taskId = :taskId", Task.class);
			query.setParameter("taskId", taskId);
			return query.getSingleResult();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get task by id");
		}	
	}

	@Override
	@Transactional
	public Task createTask(Story story, String title, String description, Status status, User user,
			Score score, Priority priority) {
		try{
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
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to create task");
		}	
	}

	@Override
	@Transactional
	public void updatePriority(Task task, Priority priority) {
		try{
			final Query query = em.createQuery("update Task set priority = :priority where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("priority", priority);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update priority");
		}	
	}

	@Override
	@Transactional
	public void updateScore(Task task, Score score) {
		try {
			final Query query = em.createQuery("update Task set score = :score where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("score", score);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update task score");
		}	
	}

	@Override
	@Transactional
	public Story getParent(Task task) {
		try{
			return task.story();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get task parents");
		}	
	}

	@Override
	@Transactional
	public void updateTitle(Task task, String title) {
		try {
			final Query query = em.createQuery("update Task set title = :title where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("title", title);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update task title");
		}	
	}

	@Override
	@Transactional
	public void updateDescription(Task task, String description) {
		try{
			final Query query = em.createQuery("update Task set description = :description where taskId = :taskId");
			query.setParameter("taskId", task.taskId());
			query.setParameter("description", description);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update task description");
		}	
	}

	@Override
	@Transactional
	public List<Task> getUnfinishedTasks(Story oldStory) {
		try{
			final TypedQuery<Task> query = em.createQuery("from Task task where task.story = :story and (task.status = :notStarted or task.status = :started)", Task.class);
			query.setParameter("story", oldStory);
			query.setParameter("notStarted", Status.NOT_STARTED);
			query.setParameter("started", Status.STARTED);
			return query.getResultList();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get unfinished tasks");
		}
	}

	@Override
	@Transactional
	public void cloneTaskToStory(Task task, Story story) {
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
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to clone task ");
		}
	}

}
