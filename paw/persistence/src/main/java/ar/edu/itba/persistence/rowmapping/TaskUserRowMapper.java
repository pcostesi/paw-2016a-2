package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskUserRowMapper implements RowMapper<Task> {
	@Override
    public Task mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Task(rs.getInt("task_id"), rs.getString("title"), rs.getString("description"), 
            		TaskStatus.getByValue(rs.getInt("status")), 
            		new User(rs.getString("username"), rs.getString("password"), rs.getString("mail")));
    }
}
