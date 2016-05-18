package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.PersistableTask;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;
import ar.edu.itba.persistence.rowmapping.StoryRowMapper;
import ar.edu.itba.persistence.rowmapping.TaskUserRowMapper;

@Repository
public class TaskJdbcDao implements TaskDao{
	
	private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final TaskUserRowMapper taskRowMapper;
    private final StoryRowMapper storyRowMapper;

    @Autowired
    public TaskJdbcDao(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
            taskRowMapper = new TaskUserRowMapper();
            storyRowMapper = new StoryRowMapper();
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("task").usingGeneratedKeyColumns("task_id");
    }
	
	@Override
	public Task createTask(Story story, String title, Optional<String> description, Status status, Optional<User> owner, Score score, Priority priority) {
		
		final String ownerUsername = owner.isPresent()? owner.get().username() : null;
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("story_id", story.storyId());
		args.put("title", title);
		args.put("description", description);
		args.put("owner", ownerUsername);
		args.put("status", status.getValue());
		args.put("score", score.getValue());
		args.put("priority", priority.getValue());

		try {
			int taskId = jdbcInsert.executeAndReturnKey(args).intValue();		
			return PersistableTask.builder()
					.taskId(taskId)
					.title(title)
					.description(description)
					.status(status)
					.score(score)
					.owner(Optional.ofNullable(ownerUsername))
					.priority(priority)
					.storyId(story.storyId())
					.build();
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to create task");
        }
	}
	
	@Override
	public void deleteTask(Task task) {
		try {
			jdbcTemplate.update("DELETE FROM task WHERE task_id = ?", task.taskId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete task");
        }
	}

	@Override
	public Task updateOwner(Task task, final Optional<User> user) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			String ownerUsername = user.isPresent()? user.get().username() : null;
			persistableTask.setOwner(Optional.ofNullable(ownerUsername));
			jdbcTemplate.update("UPDATE task SET owner = ? WHERE task_id = ?", ownerUsername, task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update owner");
        }
	}

	@Override
	public Task updateStatus(Task task, Status status) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			persistableTask.setStatus(status);
			jdbcTemplate.update("UPDATE task SET status = ? WHERE task_id = ?", status.getValue(), task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update status");
        }
	}

	@Override
	public Task getTaskById(int taskId) {
		try {
			List<Task> tasks = jdbcTemplate.query("SELECT * FROM task WHERE task_id = ?", taskRowMapper, taskId);
		
			if (tasks.isEmpty()) {
				return null;
			} else {
				return tasks.get(0);
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get task by ID");
        }
	}

	@Override
	public List<Task> getTasksForStory(Story story) {
		try {
			return jdbcTemplate.query("SELECT * FROM task WHERE story_id = ?", taskRowMapper, story.storyId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get tasks for story");
        }
	}

	@Override
	public boolean taskExists(Task task) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE task_id = ?", Integer.class, task.taskId()) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check task exists");
        }
	}
	
	@Override
	public boolean taskExists(Story story, String title) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE story_id = ? AND title = ?", Integer.class, story.storyId(), title) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check task exists by story ID and title");
        }
	}

	@Override
	public Task updatePriority(Task task, Priority priority) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			persistableTask.setPriority(priority);
			jdbcTemplate.update("UPDATE task SET priority = ? WHERE task_id = ?", priority.getValue(), task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update task priority");
        }
	}

	@Override
	public Task updateScore(Task task, Score score) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			persistableTask.setScore(score);
			jdbcTemplate.update("UPDATE task SET score = ? WHERE task_id = ?", score.getValue(), task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update task score");
        }
	}

	@Override
	public Story getParent(Task task) {
		try {
			return jdbcTemplate.queryForObject("SELECT story_id FROM task WHERE task_id = ?", storyRowMapper, task.storyId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get task parent");
        }
	}

	@Override
	public Task updateTitle(Task task, String title) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			persistableTask.setTitle(title);
			jdbcTemplate.update("UPDATE task SET title = ? WHERE task_id = ?", title, task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update title");
        }
	}

	@Override
	public Task updateDescription(Task task, Optional<String> description) {
		try {
			PersistableTask persistableTask = (PersistableTask) task;
			persistableTask.setDescription(description);
			jdbcTemplate.update("UPDATE task SET description = ? WHERE task_id = ?", description.orElse(null), task.taskId());
			return persistableTask;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update description");
        }
	}

}
