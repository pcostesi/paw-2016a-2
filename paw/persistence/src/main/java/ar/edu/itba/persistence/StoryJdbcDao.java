package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Story;
import ar.edu.itba.persistence.rowmapping.StoryRowMapper;

public class StoryJdbcDao implements StoryDao{

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private StoryRowMapper storyRowMapper;

    @Autowired
    public StoryJdbcDao(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
            storyRowMapper = new StoryRowMapper();
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("story").usingGeneratedKeyColumns("story_id");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS story ("
            				+ "story_id INTEGER NOT NULL IDENTITY,"
            				+ "iteration_id INTEGER NOT NULL,"
                    		+ "title varchar(100) NOT NULL,"
                            + "PRIMARY KEY ( story_id ),"
                            + "FOREIGN KEY ( iteration_id ) REFERENCES iteration ( iteration_id ) ON DELETE CASCADE,"
                            + "UNIQUE ( iteration_id, title )"
                    + ")");
    }
	
	@Override
	public List<Story> getStoriesForIteration(int iterationId) {
		return jdbcTemplate.query("SELECT * FROM story WHERE story_id = ?", storyRowMapper, iterationId);
	}

	@Override
	public boolean storyExists(int storyId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM story WHERE story_id = ?", Integer.class, storyId) == 1;
	}

	@Override
	public boolean hasTaskWithName(int storyId, String title) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE story_id = ? AND title = ?", Integer.class, storyId, title) == 1;
	}

	@Override
	public Story createStory(int iterationId, String title) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("iteration_id", iterationId);
		args.put("title", title);

		int storyId = jdbcInsert.executeAndReturnKey(args).intValue();
		
		return new Story(storyId, title);
	}

	@Override
	public Story getStoryById(int storyId) {
		List<Story> stories = jdbcTemplate.query("SELECT * FROM story WHERE story_id = ?", storyRowMapper, storyId);
		
		if (stories.isEmpty()) {
			return null;
		} else {
			return stories.get(0);
		}
	}

	@Override
	public void updateName(int storyId, String title) {
		jdbcTemplate.update("UPDATE story SET title = ? WHERE story_id = ?", title, storyId);
	}

	@Override
	public void deleteStory(int storyId) {
		jdbcTemplate.update("DELETE FROM story WHERE story_id = ?", storyId);
	}

}
