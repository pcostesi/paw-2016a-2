package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectStatus;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            return new Project(rs.getInt("project_id"), rs.getString("name"), rs.getString("code"), rs.getString("description"),
            		new Date(rs.getDate("date_start").getTime()), ProjectStatus.getByValue(rs.getInt("status")));
    }
}
