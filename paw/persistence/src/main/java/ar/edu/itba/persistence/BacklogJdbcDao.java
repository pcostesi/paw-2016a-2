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

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.ImmutableBacklogItem;
import ar.edu.itba.persistence.rowmapping.BacklogRowMapper;

@Repository
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
	public BacklogItem createBacklogItem(String title, String description, int projectId) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("title", title);
		args.put("description", description);
		args.put("project_id", projectId);

		try {
			int itemId = jdbcInsert.executeAndReturnKey(args).intValue();
			return ImmutableBacklogItem.builder()
					.title(title)
					.description(Optional.ofNullable(description))
					.backlogItemId(itemId)
					.build();
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to create backlog item");
		}
	}

	@Override
	public boolean backlogItemExists(int itemId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM backlog WHERE item_id = ?", Integer.class, itemId) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check backlog item exists");
		}
	}

	@Override
	public boolean backlogItemExists(String title, int projectId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM backlog WHERE title = ? AND project_id = ?", Integer.class, title, projectId) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check there is another backlog item with this title in this project");
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
			return jdbcTemplate.query("SELECT * FROM backlog WHERE project_id = ?", backlogRowMapper, projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get backlog items for project");
        }
	}

	@Override
	public void updateTitle(int itemId, String title) {
		try {
			jdbcTemplate.update("UPDATE backlog SET title = ? WHERE item_id = ?", title, itemId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update backlog item title");
        }
	}

	@Override
	public void updateDescription(int itemId, String description) {
		try {
			jdbcTemplate.update("UPDATE backlog SET description = ? WHERE item_id = ?", description, itemId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update backlog item description");
        }
	}

	@Override
	public int getParent(int backlogItemId) {
		try {
			return jdbcTemplate.queryForObject("SELECT project_id FROM backlog WHERE item_id = ?", Integer.class, backlogItemId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get task parent ID");
        }
	}

	@Override
	public BacklogItem getBacklogItemById(int itemId) {
		try {
			List<BacklogItem> backlog = jdbcTemplate.query("SELECT * FROM backlog WHERE item_id = ?", backlogRowMapper, itemId);
			
			if (backlog.isEmpty()) {
				return null;
			} else {
				return backlog.get(0);
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get backlog item by id");
        }
	}

}