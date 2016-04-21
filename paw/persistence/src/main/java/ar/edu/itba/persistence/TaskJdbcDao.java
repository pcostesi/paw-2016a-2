package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
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

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS task ("
            				+ "task_id INTEGER NOT NULL IDENTITY,"
            				+ "story_id INTEGER NOT NULL,"
                    		+ "title varchar(100) NOT NULL,"
                            + "description varchar(500) NOT NULL,"
                            + "owner varchar(100),"
                            + "status INTEGER NOT NULL,"
                            + "FOREIGN KEY ( story_id ) REFERENCES story ( story_id ) ON DELETE CASCADE,"
                            + "UNIQUE ( story_id, title )"
                    + ")");
    }
	
	@Override
	public Task createTask(int storyId, String title, String description) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("story_id", storyId);
		args.put("title", title);
		args.put("description", description);
		args.put("owner", null);
		args.put("status", TaskStatus.NOT_STARTED.getValue());

		int taskId = jdbcInsert.executeAndReturnKey(args).intValue();
		
		return new Task(taskId, title, description, TaskStatus.NOT_STARTED, null);
	}
	
	@Override
	public void deleteTask(final int taskId) {
		jdbcTemplate.update("DELETE FROM task WHERE task_id = ?", taskId);
	}

	@Override
	public void updateOwner(final int taskId,final  String username) {
		jdbcTemplate.update("UPDATE task SET owner = ? WHERE task_id = ?", username, taskId);
	}

	@Override
	public void updateStatus(final int taskId,final  int statusValue) {
		jdbcTemplate.update("UPDATE task SET status = ? WHERE task_id = ?", statusValue, taskId);
	}

	@Override
	public Task getTaskById(int taskId) {
		List<Task> tasks = jdbcTemplate.query("SELECT * FROM task LEFT JOIN user ON user.username = task.owner WHERE task_id = ?", taskUserRowMapper, taskId);
	
		if (tasks.isEmpty()) {
			return null;
		} else {
			return tasks.get(0);
		}
	}

	@Override
	public List<Task> getTasksForStory(int storyId) {
		return jdbcTemplate.query("SELECT * FROM task LEFT JOIN user ON user.username = task.owner WHERE story_id = ?", taskUserRowMapper, storyId);
	}

	@Override
	public boolean taskExists(int taskId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE task_id = ?", Integer.class, taskId) == 1;
	}

}
