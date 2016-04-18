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
import ar.edu.itba.models.IterationDetail;
import ar.edu.itba.models.ProjectDetail;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;
import ar.edu.itba.persistence.rowmapping.IterationDetailRowMapper;
import ar.edu.itba.persistence.rowmapping.ProjectDetailRowMapper;
import ar.edu.itba.persistence.rowmapping.TaskUserRowMapper;

@Repository
public class TaskJdbcDao implements TaskDao{
	
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TaskJdbcDao(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("task").usingGeneratedKeyColumns("task_id");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS task ("
            				+ "task_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
            				+ "iteration_id INTEGER NOT NULL,"
                    		+ "title varchar(100) NOT NULL,"
                            + "description varchar(500) NOT NULL,"
                            + "owner varchar(100),"
                            + "status INTEGER NOT NULL,"
                            + "PRIMARY KEY ( task_id )"
                    + ")");
    }

	@Override
	public Task createTask(String projectName, int iterationNumber, String title, String description) {		
		List<ProjectDetail> project = jdbcTemplate.query("SELECT * FROM project WHERE name = ? LIMIT 1", new ProjectDetailRowMapper(), projectName);
		if (project.isEmpty()) {
			return null;
		}
		
		int projectId = project.get(0).getProjectId();
		
		List<IterationDetail> iteration = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id= ? AND number = ? LIMIT 1", new IterationDetailRowMapper(), projectId, iterationNumber);
		if (iteration.isEmpty()) {
			return null;
		}
		
		int iterationId = iteration.get(0).getIterationId();
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("iteration_id", iterationId);
		args.put("title", title);
		args.put("description", description);
		args.put("owner", null);
		args.put("status", TaskStatus.NOT_STARTED.getValue());
        jdbcInsert.execute(args);
		
		return new Task(iterationId, description, description, TaskStatus.NOT_STARTED, null);
	}

	@Override
	public boolean deleteTask(int taskId) {
		return jdbcTemplate.update("DELETE FROM task WHERE task_id = ?", taskId) > 0;
	}

	@Override
	public boolean changeOwnership(int taskId, User user) {
		String queryUser = (user == null)? null: user.getUsername();
		return jdbcTemplate.update("UPDATE task SET owner = ? WHERE task_id = ?", queryUser, taskId) > 0;
	}

	@Override
	public boolean changeStatus(int taskId, TaskStatus status) {
		return jdbcTemplate.update("UPDATE task SET status = ? WHERE task_id = ?", status.getValue(), taskId) > 0;
	}

	@Override
	public Task getTask(int taskId) {
		List<Task> taskList = jdbcTemplate.query("SELECT * FROM task INNER JOIN iteration "
				+ "ON task.iteration_id = iteration.iteration_id WHERE task_id = ?", new TaskUserRowMapper(), taskId);
		if (taskList.isEmpty()) {
			return null;
		} else {
			return taskList.get(0);
		}
	}

}
