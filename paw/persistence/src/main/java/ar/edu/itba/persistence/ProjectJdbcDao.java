package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectStatus;

public class ProjectJdbcDao implements ProjectDao{
	
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private ProjectRowMapper projectRowMapper;

    @Autowired
    public ProjectJdbcDao(final DataSource ds) {
    		projectRowMapper = new ProjectRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("project");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS project ("
                            + "project_id INTEGER NOT NULL AUTO_INCREMENT,"
                            + "name varchar(100),"
                            + "description varchar(500),"
                            + "date_start DATE,"
                            + "status INTEGER,"
                            + "PRIMARY KEY ( project_id )"
                    + ")");

    }
    
	@Override
	public List<Project> getProjetcs() {
		final List<Project> list = jdbcTemplate.query("SELECT * FROM project", projectRowMapper);
        if (list.isEmpty()) {
                return null;
        }

        return list;
	}

	@Override
	public Project createProject(final String name, final String description, final Date startDate, final ProjectStatus status) {
		final Map<String, Object> args = new HashMap<String, Object>();
        args.put("name", name);
        args.put("description", description);
        args.put("start_date", new java.sql.Date(startDate.getTime()));
        args.put("status", status.getValue());
        jdbcInsert.execute(args);

        return new Project(name, description, startDate, status);
	}
	
	private static class ProjectRowMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                return new Project(rs.getString("name"), rs.getString("description"),
                		new Date(rs.getDate("start_date").getTime()), ProjectStatus.getByValue(rs.getInt("status")));
        }
    }

}
