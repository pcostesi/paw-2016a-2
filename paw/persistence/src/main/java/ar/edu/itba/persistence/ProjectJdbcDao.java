package ar.edu.itba.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectStatus;
import ar.edu.itba.persistence.rowmapping.ProjectRowMapper;

@Repository
public class ProjectJdbcDao implements ProjectDao{
	
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private ProjectRowMapper projectRowMapper;

    @Autowired
    public ProjectJdbcDao(final DataSource ds) {
    		projectRowMapper = new ProjectRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("project").usingGeneratedKeyColumns("project_id");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS project ("
                            + "project_id INTEGER NOT NULL IDENTITY,"
                            + "name varchar(100) NOT NULL,"
                            + "code varchar(10) NOT NULL,"
                            + "description varchar(500) NOT NULL,"
                            + "date_start DATE NOT NULL,"
                            + "status INTEGER NOT NULL,"
                            + "UNIQUE ( project_id, name ),"
                            + "UNIQUE ( name ),"
                            + "UNIQUE ( code )"
                    + ")");
    }

	@Override
	public Project createProject(String name, String description, String code) {
		
		Date curDate = new Date();
		Map<String, Object> args = new HashMap<String, Object>();
		
        args.put("name", name);
        args.put("description", description);
        args.put("code", code);
        args.put("date_start", new java.sql.Date(new Date().getTime()));
        args.put("status", ProjectStatus.OPEN.getValue());
        
        int projectId = jdbcInsert.executeAndReturnKey(args).intValue();

        return new Project(projectId, name, code, description, curDate, ProjectStatus.OPEN);
	}
	
	@Override
	public void deleteProject(final int projectId) {
		jdbcTemplate.update("DELETE FROM project WHERE project_id = ?", projectId);
	}
	
	@Override
	public List<Project> getProjects() {
        return jdbcTemplate.query("SELECT * FROM project", projectRowMapper);
	}

	@Override
	public boolean projectExists(int projectId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE project_id = ?", Integer.class, projectId) == 1;
	}

	@Override
	public boolean projectNameExists(String name) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE name = ?", Integer.class, name) == 1;
	}

	@Override
	public boolean projectCodeExists(String code) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE code = ?", Integer.class, code) == 1;
	}

	@Override
	public void updateName(int projectId, String name) {
		jdbcTemplate.update("UPDATE project SET name = ? WHERE project_id = ?", name, projectId);
	}

	@Override
	public void updateDescription(int projectId, String description) {
		jdbcTemplate.update("UPDATE project SET description = ? WHERE project_id = ?", description, projectId);
	}

	@Override
	public void updateCode(int projectId, String code) {
		jdbcTemplate.update("UPDATE project SET code = ? WHERE project_id = ?", code, projectId);
	}

	@Override
	public Project getProjectById(int projectId) {
		List<Project> project = jdbcTemplate.query("SELECT * FROM project WHERE project_id = ?", projectRowMapper, projectId);
		
		if (project.isEmpty()) {
			return null;
		} else {
			return project.get(0);
		}
	}

	@Override
	public Project getProjectByCode(String code) {
		List<Project> project = jdbcTemplate.query("SELECT * FROM project WHERE code = ?", projectRowMapper, code);
		
		if (project.isEmpty()) {
			return null;
		} else {
			return project.get(0);
		}
	}
}
