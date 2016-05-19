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

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.persistence.rowmapping.BacklogRowMapper;
import ar.edu.itba.persistence.rowmapping.ProjectRowMapper;

@Repository
public class BacklogJdbcDao implements BacklogDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final BacklogRowMapper backlogRowMapper;
	private final ProjectRowMapper projectRowMapper;

	@Autowired
	public BacklogJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		backlogRowMapper = new BacklogRowMapper();
		projectRowMapper = new ProjectRowMapper();
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("backlog").usingGeneratedKeyColumns("item_id");
	}

	@Override
	public BacklogItem createBacklogItem(String title, String description, Project project) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("title", title);
		args.put("description", description);
		args.put("project_id", project.projectId());

		try {
			int itemId = jdbcInsert.executeAndReturnKey(args).intValue();
			return BacklogItem.builder()
					.title(title)
					.description(description)
					.backlogItemId(itemId)
					.build();
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to create backlog item");
		}
	}

	@Override
	public boolean backlogItemExists(BacklogItem backlogItem) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM backlog WHERE item_id = ?", Integer.class, backlogItem.backlogItemId()) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check backlog item exists");
		}
	}

	@Override
	public boolean backlogItemExists(Project project, String title) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM backlog WHERE title = ? AND project_id = ?", Integer.class, title, project.projectId()) == 1;
		} catch (DataAccessException exception) {
			throw new IllegalStateException("Database failed to check there is another backlog item with this title in this project");
		}
	}

	@Override
	public void deleteItem(BacklogItem backlogItem) {
		try {
			jdbcTemplate.update("DELETE FROM backlog WHERE item_id = ?", backlogItem.backlogItemId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete backlog item");
        }
	}

	@Override
	public List<BacklogItem> getBacklogForProject(Project project) {
		try {
			return jdbcTemplate.query("SELECT * FROM backlog WHERE project_id = ?", backlogRowMapper, project.projectId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get backlog items for project");
        }
	}

	@Override
	public void updateTitle(BacklogItem backlogItem, String title) {
		try {
			jdbcTemplate.update("UPDATE backlog SET title = ? WHERE item_id = ?", title, backlogItem.backlogItemId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update backlog item title");
        }
	}

	@Override
	public void updateDescription(BacklogItem backlogItem, String description) {
		try {
			jdbcTemplate.update("UPDATE backlog SET description = ? WHERE item_id = ?", description, backlogItem.backlogItemId());
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update backlog item description");
        }
	}

	@Override
	public Project getParent(BacklogItem backlogItem) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM project WHERE project_id = ?", projectRowMapper, backlogItem.project().projectId());
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