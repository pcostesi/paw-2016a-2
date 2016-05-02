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

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Story;
import ar.edu.itba.persistence.rowmapping.StoryRowMapper;

@Repository
public class StoryJdbcDao implements StoryDao{

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private StoryRowMapper storyRowMapper;

    @Autowired
    public StoryJdbcDao(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
            storyRowMapper = new StoryRowMapper();
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("story").usingGeneratedKeyColumns("story_id");
    }
	
	@Override
	public List<Story> getStoriesForIteration(int iterationId) {
		try {
			return jdbcTemplate.query("SELECT * FROM story WHERE iteration_id = ?", storyRowMapper, iterationId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get stories for iteration");
        }
	}

	@Override
	public boolean storyExists(int storyId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM story WHERE story_id = ?", Integer.class, storyId) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check story exists");
        }
	}

	@Override
	public Story createStory(int iterationId, String title) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("iteration_id", iterationId);
		args.put("title", title);
		
		try {
			int storyId = jdbcInsert.executeAndReturnKey(args).intValue();			
			return new Story(storyId, title);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to create story");
        }
	}

	@Override
	public Story getStoryById(int storyId) {
		try {
			List<Story> stories = jdbcTemplate.query("SELECT * FROM story WHERE story_id = ?", storyRowMapper, storyId);
			
			if (stories.isEmpty()) {
				return null;
			} else {
				return stories.get(0);
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get story by ID");
        }
	}

	@Override
	public void updateName(int storyId, String title) {
		try {
			jdbcTemplate.update("UPDATE story SET title = ? WHERE story_id = ?", title, storyId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update name");
        }
	}

	@Override
	public void deleteStory(int storyId) {
		try {
			jdbcTemplate.update("DELETE FROM story WHERE story_id = ?", storyId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete story");
        }
	}
	
	@Override
	public boolean storyExists(int iterationId, String title) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM story WHERE iteration_id = ? AND title = ?", Integer.class, iterationId, title) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check story exists");
        }
	}

	@Override
	public int getParentId(int storyId) {
		try {
			return jdbcTemplate.queryForObject("SELECT iteration_id FROM story WHERE story_id = ?", Integer.class, storyId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get story parent ID");
        }
	}

}
