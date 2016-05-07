package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.Iteration;

public class IterationRowMapper implements RowMapper<Iteration> {

    @Override
    public Iteration mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Iteration(rs.getInt("iteration_id"), rs.getInt("number"), 
            		new Date(rs.getDate("date_start").getTime()), new Date(rs.getDate("date_end").getTime()));
    }
}
