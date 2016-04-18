package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.IterationDetail;

public class IterationDetailRowMapper implements RowMapper<IterationDetail> {

    @Override
    public IterationDetail mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new IterationDetail(rs.getInt("iteration_id"), rs.getInt("number"), 
            		new Date(rs.getDate("date_start").getTime()), new Date(rs.getDate("date_end").getTime()));
    }
}
