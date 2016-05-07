package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.ImmutableTask;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskPriority;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;
import ar.edu.itba.persistence.rowmapping.TaskUserRowMapper;

@Repository
public class TaskJdbcDao implements TaskDao{
	
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private TaskUserRowMapper taskUserRowMapper;

    @Autowired
    public TaskJdbcDao(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
            taskUserRowMapper = new TaskUserRowMapper();
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("task").usingGeneratedKeyColumns("task_id");
    }
	
	@Override
	public Task createTask(int storyId, String title, String description, TaskStatus status, User owner, TaskScore score) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("story_id", storyId);
		args.put("title", title);
		args.put("description", description);
		if (owner == null) {
			args.put("owner", null);
		} else {
			args.put("owner", owner.username());
		}		
		args.put("status", status.getValue());
		args.put("score", score.getValue());
		args.put("priority", TaskPriority.NORMAL.getValue());

		try {
			int taskId = jdbcInsert.executeAndReturnKey(args).intValue();		
			return ImmutableTask.builder()
					.taskId(taskId)
					.title(title)
					.description(description)
					.status(TaskStatus.NOT_STARTED)
					.score(TaskScore.NORMAL)
					.priority(TaskPriority.NORMAL)
					.story(storyId)
					.build();
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to create task");
        }
	}
	
	@Override
	public void deleteTask(final int taskId) {
		try {
			jdbcTemplate.update("DELETE FROM task WHERE task_id = ?", taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete task");
        }
	}

	@Override
	public void updateOwner(final int taskId,final  String username) {
		try {
			jdbcTemplate.update("UPDATE task SET owner = ? WHERE task_id = ?", username, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update owner");
        }
	}

	@Override
	public void updateStatus(final int taskId,final  int statusValue) {
		try {
			jdbcTemplate.update("UPDATE task SET status = ? WHERE task_id = ?", statusValue, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update status");
        }
	}

	@Override
	public Task getTaskById(int taskId) {
		try {
			List<Task> tasks = jdbcTemplate.query("SELECT * FROM task LEFT JOIN account ON account.username = task.owner WHERE task_id = ?", taskUserRowMapper, taskId);
		
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
	public List<Task> getTasksForStory(int storyId) {
		try {
			return jdbcTemplate.query("SELECT * FROM task LEFT JOIN account ON account.username = task.owner WHERE story_id = ?", taskUserRowMapper, storyId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get tasks for story");
        }
	}

	@Override
	public boolean taskExists(int taskId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE task_id = ?", Integer.class, taskId) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check task exists");
        }
	}
	
	@Override
	public boolean taskExists(int storyId, String title) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE story_id = ? AND title = ?", Integer.class, storyId, title) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check task exists by story ID and title");
        }
	}

	@Override
	public void updatePriority(int taskId, int priorityValue) {
		try {
			jdbcTemplate.update("UPDATE task SET priority = ? WHERE task_id = ?", priorityValue, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update task priority");
        }
	}

	@Override
	public void updateScore(int taskId, int scoreValue) {
		try {
			jdbcTemplate.update("UPDATE task SET score = ? WHERE task_id = ?", scoreValue, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update task score");
        }
	}

	@Override
	public int getParentId(int taskId) {
		try {
			return jdbcTemplate.queryForObject("SELECT story_id FROM task WHERE task_id = ?", Integer.class, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get task parent ID");
        }
	}

	@Override
	public void updateTitle(int taskId, String title) {
		try {
			jdbcTemplate.update("UPDATE task SET title = ? WHERE task_id = ?", title, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update title");
        }
	}

	@Override
	public void updateDescription(int taskId, String description) {
		try {
			jdbcTemplate.update("UPDATE task SET description = ? WHERE task_id = ?", description, taskId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update description");
        }
	}

}
