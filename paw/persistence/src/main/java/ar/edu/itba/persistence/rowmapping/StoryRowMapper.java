package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.ImmutableStory;
import ar.edu.itba.models.Story;

public class StoryRowMapper implements RowMapper<Story> {

    @Override
    public Story mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            return ImmutableStory.builder()
            		.storyId(rs.getInt("story_id"))
            		.title(rs.getString("title"))
            		.iteration(rs.getInt("iteration_id"))
            		.build();
    }
}
