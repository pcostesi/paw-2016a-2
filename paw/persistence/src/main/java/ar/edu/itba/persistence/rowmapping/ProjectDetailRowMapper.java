package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.ProjectDetail;
import ar.edu.itba.models.ProjectStatus;

public class ProjectDetailRowMapper implements RowMapper<ProjectDetail> {

    @Override
    public ProjectDetail mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new ProjectDetail(rs.getInt("project_id"), rs.getString("name"), rs.getString("description"),
            		new Date(rs.getDate("date_start").getTime()), ProjectStatus.getByValue(rs.getInt("status")));
    }
}
