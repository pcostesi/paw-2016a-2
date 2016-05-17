package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.HibernateTask;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

public class TaskHibernateDao implements TaskDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Task> getTasksForStory(int storyId) {
		final TypedQuery<HibernateTask> query = em.createQuery("from HibernateTask task where task.storyId = :storyId", HibernateTask.class);
        query.setParameter("storyId", storyId);
        return query.getResultList();
	}

	@Override
	public boolean taskExists(int taskId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean taskExists(int storyId, String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateStatus(int taskId, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOwner(int taskId, Optional<User> user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTask(int taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task getTaskById(int taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task createTask(int storyId, String name, String description, Status status, Optional<User> user,
			Score score) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePriority(int taskId, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScore(int taskId, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getParentId(int taskId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateTitle(int taskId, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDescription(int taskId, String description) {
		// TODO Auto-generated method stub
		
	}

}
