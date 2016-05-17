package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.ImmutableIteration;
import ar.edu.itba.models.Iteration;

public class IterationRowMapper implements RowMapper<Iteration> {

    @Override
    public Iteration mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return ImmutableIteration.builder()
            		.iterationId(rs.getInt("iteration_id"))
            		.number(rs.getInt("number"))
            		.startDate(rs.getDate("date_start").toLocalDate())
            		.endDate(rs.getDate("date_end").toLocalDate())
            		.build();
    }
}
