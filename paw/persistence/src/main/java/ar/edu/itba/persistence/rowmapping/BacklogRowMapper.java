package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.BacklogItem;

public class BacklogRowMapper implements RowMapper<BacklogItem>{

	@Override
	public BacklogItem mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        return new BacklogItem(rs.getString("name"), rs.getString("description"), rs.getInt("item_id"));
}
}
