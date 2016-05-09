package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.ImmutableBacklogItem;

public class BacklogRowMapper implements RowMapper<BacklogItem>{

	@Override
	public BacklogItem mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        return ImmutableBacklogItem.builder()
        		.title(rs.getString("name"))
        		.description(rs.getString("description"))
        		.backlogItemId(rs.getInt("item_id"))
        		.build();
}
}
