package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.PersistableTask;
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
	public List<Task> getTasksForStory(Story story) {
		return story.tasks();
	}

	@Override
	public boolean taskExists(Task task) {
		return em.contains(task);
	}

	@Override
	public boolean taskExists(Story story, String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Task task where task.title = :title and task.storyId = :storyId", Integer.class);
		query.setParameter("title", title);
		query.setParameter("storyId", story.storyId());
		return query.getSingleResult() > 0;
	}

	@Override
	public Task updateStatus(Task task, Status status) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setStatus(status);
		return em.merge(persistableTask);
	}

	@Override
	public Task updateOwner(Task task, Optional<User> owner) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setOwner(Optional.ofNullable(owner.isPresent()? owner.get().username():null));
		return em.merge(persistableTask);
	}

	@Override
	public void deleteTask(Task task) {
		em.remove(task);
	}

	@Override
	public Task getTaskById(int taskId) {
		return em.find(Task.class, taskId);
	}

	@Override
	public Task createTask(Story story, String title, Optional<String> description, Status status, Optional<User> user,
			Score score, Priority priority) {
		final PersistableTask persistableTask = PersistableTask.builder()
				.title(title)
				.description(description)
				.status(status)
				.owner(user.isPresent()? user.get().username() : null)
				.score(score)
				.priority(priority)
				.storyId(story.storyId())
				.build();
		em.persist(persistableTask);
		em.flush();
		return persistableTask;
	}

	@Override
	public Task updatePriority(Task task, Priority priority) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setPriority(priority);
		return em.merge(persistableTask);
	}

	@Override
	public Task updateScore(Task task, Score score) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setScore(score);
		return em.merge(persistableTask);
	}

	@Override
	public Story getParent(Task task) {
		return em.find(Story.class, task.storyId());
	}

	@Override
	public Task updateTitle(Task task, String title) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setTitle(title);
		return em.merge(persistableTask);
	}

	@Override
	public Task updateDescription(Task task, Optional<String> description) {
		PersistableTask persistableTask = (PersistableTask) task;
		persistableTask.setDescription(description);
		return em.merge(persistableTask);
	}

}
