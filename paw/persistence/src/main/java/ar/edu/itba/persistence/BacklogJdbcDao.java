package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.persistence.rowmapping.BacklogRowMapper;

public class BacklogJdbcDao implements BacklogDao {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private BacklogRowMapper backlogRowMapper;

	@Autowired
	public BacklogJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		backlogRowMapper = new BacklogRowMapper();
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("backlog").usingGeneratedKeyColumns("item_id");
	}

	@Override
	public BacklogItem createBacklogItem(String name, String description, int projectId) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("title", name);
		args.put("title", description);
		args.put("iteration_id", projectId);

		try {
			int itemId = jdbcInsert.executeAndReturnKey(args).intValue();
			return new BacklogItem(name, description, itemId);
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to create story");
		}
	}

	@Override
	public boolean backlogItemExists(int itemId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM backlog WHERE item_id = ?", Integer.class,
					itemId) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check backlog item exists");
		}
	}

	@Override
	public boolean backlogItemExists(String name, String description, int projectId) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM backlog WHERE name = ? AND description = ? AND project_id = ?", Integer.class,
					name, description, projectId) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check story exists");
		}
	}

	@Override
	public void deleteItem(int itemId) {
		try {
			jdbcTemplate.update("DELETE FROM backlog WHERE item_id = ?", itemId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete backlog item");
        }
	}

	@Override
	public List<BacklogItem> getBacklogForProject(int projectId) {
		try {
			return jdbcTemplate.query("SELECT * FROM backlog WHERE item_id = ?", backlogRowMapper, projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get stories for iteration");
        }
	}

}
