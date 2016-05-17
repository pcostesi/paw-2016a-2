package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

public class TaskHibernateDao implements TaskDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Task> getTasksForStory(int storyId) {
		final TypedQuery<Task> query = em.createQuery("from Task task where task.storyId = :storyId", Task.class);
        query.setParameter("storyId", storyId);
        return query.getResultList();
	}

	@Override
	public boolean taskExists(int taskId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Task task where task.taskId = :taskId", Integer.class);
        query.setParameter("taskId", taskId);
        return query.getSingleResult() > 0;
	}

	@Override
	public boolean taskExists(int storyId, String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Task task where task.title = :title and task.storyId = :storyId", Integer.class);
		query.setParameter("title", title);
		query.setParameter("storyId", storyId);
        return query.getSingleResult() > 0;
	}

	@Override
	public void updateStatus(int taskId, int value) {
		final TypedQuery<Integer> query = em.createQuery("update Task set status = :status where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("status", Status.getByValue(value));
		query.executeUpdate();
	}

	@Override
	public void updateOwner(int taskId, Optional<User> user) {
		final TypedQuery<Integer> query = em.createQuery("update Task set owner = :owner where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("owner", user.isPresent()? user.get().username() : null);
		query.executeUpdate();
	}

	@Override
	public void deleteTask(int taskId) {
		final TypedQuery<Integer> query = em.createQuery("delete from Task where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.executeUpdate();
	}

	@Override
	public Task getTaskById(int taskId) {
		final TypedQuery<Task> query = em.createQuery("from Task task where task.taskId = :taskId", Task.class);
        query.setParameter("taskId", taskId);
        return query.getSingleResult();
	}

	@Override
	public Task createTask(int storyId, String title, String description, Status status, Optional<User> user,
			Score score, Priority priority) {
		final Task task = Task.builder()
				.title(title)
				.description(description)
				.status(status)
				.owner(user.isPresent()? user.get().username() : null)
				.score(score)
				.priority(priority)
				.storyId(storyId)
				.build();
		em.persist(task);
		em.flush();
		return task;
	}

	@Override
	public void updatePriority(int taskId, int value) {
		final TypedQuery<Integer> query = em.createQuery("update Task set priority = :priority where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("status", Priority.getByValue(value));
		query.executeUpdate();
	}

	@Override
	public void updateScore(int taskId, int value) {
		final TypedQuery<Integer> query = em.createQuery("update Task set score = :score where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("score", Score.getByValue(value));
		query.executeUpdate();
	}

	@Override
	public int getParentId(int taskId) {
		final TypedQuery<Integer> query = em.createQuery("select task.storyId from Task task where task.taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public void updateTitle(int taskId, String title) {
		final TypedQuery<Integer> query = em.createQuery("update Task set title = :title where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	public void updateDescription(int taskId, String description) {
		final TypedQuery<Integer> query = em.createQuery("update Task set description = :description where taskId = :taskId", Integer.class);
		query.setParameter("taskId", taskId);
		query.setParameter("description", description);
		query.executeUpdate();
	}

}
