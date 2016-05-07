package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.ImmutableUser;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskPriority;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskUserRowMapper implements RowMapper<Task> {
	
	@Override
    public Task mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		
		Integer taskId = rs.getInt("task_id");
		String title = rs.getString("title");
		String description = rs.getString("description");
		TaskStatus status = TaskStatus.getByValue(rs.getInt("status"));
		TaskScore score = TaskScore.getByValue(rs.getInt("score"));
		TaskPriority priority = TaskPriority.getByValue(rs.getInt("priority"));
		
		String username = rs.getString("username");
		String password = rs.getString("password");
		String mail = rs.getString("mail");
		User user = (username == null)? null : ImmutableUser.builder()
				.username(username)
				.password(password)
				.mail(mail)
				.build();
	
		return new Task(taskId, title, description, status, score, priority, user);         
    }
		
}
