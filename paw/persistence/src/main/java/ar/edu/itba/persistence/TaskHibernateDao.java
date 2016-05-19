package ar.edu.itba.persistence;

import java.util.List;

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
		final TypedQuery<Task> query = em.createQuery("from Task task where task.story = :story", Task.class);
        query.setParameter("story", story);
        return query.getResultList();
	}

	@Override
	@Transactional
	public boolean taskExists(Task task) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.taskId = :taskId", Long.class);
        query.setParameter("taskId", task.taskId());
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public boolean taskExists(Story story, String title) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Task task where task.title = :title and task.story = :story", Long.class);
		query.setParameter("title", title);
		query.setParameter("story", story);
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public void updateStatus(Task task, Status status) {
		final Query query = em.createQuery("update Task set status = :status where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("status", status);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateOwner(Task task, User owner) {
		final Query query = em.createQuery("update Task set owner = :owner where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("owner", owner);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteTask(Task task) {
		final Query query = em.createQuery("delete from Task where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public Task getTaskById(int taskId) {
		final TypedQuery<Task> query = em.createQuery("from Task task where task.taskId = :taskId", Task.class);
        query.setParameter("taskId", taskId);
        return query.getSingleResult();
	}

	@Override
	@Transactional
	public Task createTask(Story story, String title, String description, Status status, User user,
			Score score, Priority priority) {
		final Task persistableTask = Task.builder()
				.title(title)
				.description(description)
				.status(status)
				.owner(user)
				.score(score)
				.priority(priority)
				.story(story)
				.build();
		em.persist(persistableTask);
		em.flush();
		return persistableTask;
	}

	@Override
	@Transactional
	public void updatePriority(Task task, Priority priority) {
		final Query query = em.createQuery("update Task set priority = :priority where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("priority", priority);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateScore(Task task, Score score) {
		final Query query = em.createQuery("update Task set score = :score where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("score", score);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public Story getParent(Task task) {
		return task.story();
	}

	@Override
	@Transactional
	public void updateTitle(Task task, String title) {
		final Query query = em.createQuery("update Task set title = :title where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateDescription(Task task, String description) {
		final Query query = em.createQuery("update Task set description = :description where taskId = :taskId");
		query.setParameter("taskId", task.taskId());
		query.setParameter("description", description);
		query.executeUpdate();
	}

}
