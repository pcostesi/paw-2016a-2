package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.task.Task;
import ar.edu.itba.models.task.TaskStatus;
import ar.edu.itba.models.user.User;

public class TaskUserRowMapper implements RowMapper<Task> {
	
	@Override
    public Task mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		
		Integer taskId = rs.getInt("task_id");
		String title = rs.getString("title");
		String description = rs.getString("description");
		TaskStatus status = TaskStatus.getByValue(rs.getInt("status"));
		
		String username = rs.getString("username");
		String password = rs.getString("password");
		String mail = rs.getString("mail");
		User user = (username == null)? null : new User(username, password, mail);
	
		return new Task(taskId, title, description, status, user);         
    }
		
}
