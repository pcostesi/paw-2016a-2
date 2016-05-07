package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.ImmutableProject;
import ar.edu.itba.models.Project;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            return ImmutableProject.builder()
            		.projectId(rs.getInt("project_id"))
            		.name(rs.getString("name"))
            		.code(rs.getString("code"))
            		.description(rs.getString("description"))
            		.startDate(new Date(rs.getDate("date_start").getTime()))
            		.build();
    }
}
