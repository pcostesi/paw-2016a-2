package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.BacklogItem;

public class BacklogRowMapper implements RowMapper<BacklogItem>{

	@Override
	public BacklogItem mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        return BacklogItem.builder()
        		.title(rs.getString("title"))
        		.description(Optional.ofNullable(rs.getString("description")))
        		.backlogItemId(rs.getInt("item_id"))
        		.build();
	}
}
