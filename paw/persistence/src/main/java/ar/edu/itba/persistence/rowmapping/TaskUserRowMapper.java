package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.PersistableTask;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Task;

public class TaskUserRowMapper implements RowMapper<Task> {
	
	@Override
    public Task mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		
		int taskId = rs.getInt("task_id");
		String title = rs.getString("title");
		String description = rs.getString("description");
		Status status = Status.getByValue(rs.getInt("status"));
		Score score = Score.getByValue(rs.getInt("score"));
		Priority priority = Priority.getByValue(rs.getInt("priority"));		
		String owner = rs.getString("owner");	
		
		return PersistableTask.builder()
				.taskId(taskId)
				.title(title)
				.description(description)
				.status(status)
				.score(score)
				.priority(priority)
				.owner(owner)
				.build();
    }
		
}
