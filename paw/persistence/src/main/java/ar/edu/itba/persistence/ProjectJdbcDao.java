package ar.edu.itba.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.ImmutableProject;
import ar.edu.itba.models.Project;
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
    }

	@Override
	public Project createProject(String name, String description, String code) {
		
		Date curDate = new Date();
		Map<String, Object> args = new HashMap<String, Object>();
		
        args.put("name", name);
        args.put("description", description);
        args.put("code", code);
        args.put("date_start", new java.sql.Date(new Date().getTime()));
        
        try {
	        int projectId = jdbcInsert.executeAndReturnKey(args).intValue();	
	        return ImmutableProject.builder()
	        		.projectId(projectId)
	        		.name(name)
	        		.code(code)
	        		.description(description)
	        		.startDate(curDate)
	        		.build();
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to create project");
	    }
	}
	
	@Override
	public void deleteProject(final int projectId) {
		try {
			jdbcTemplate.update("DELETE FROM project WHERE project_id = ?", projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to delete project");
        }
	}
	
	@Override
	public List<Project> getProjects() {
		try {
			return jdbcTemplate.query("SELECT * FROM project", projectRowMapper);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get project list");
        }
	}

	@Override
	public boolean projectExists(int projectId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE project_id = ?", Integer.class, projectId) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check project exists");
        }
	}

	@Override
	public boolean projectNameExists(String name) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE name = ?", Integer.class, name) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check project name exists");
        }
	}

	@Override
	public boolean projectCodeExists(String code) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE code = ?", Integer.class, code) == 1;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to check project code exists");
        }
	}

	@Override
	public void updateName(int projectId, String name) {
		try {
			jdbcTemplate.update("UPDATE project SET name = ? WHERE project_id = ?", name, projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update project name");
        }
	}

	@Override
	public void updateDescription(int projectId, String description) {
		try {
			jdbcTemplate.update("UPDATE project SET description = ? WHERE project_id = ?", description, projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update project description");
        }
	}

	@Override
	public void updateCode(int projectId, String code) {
		try {
			jdbcTemplate.update("UPDATE project SET code = ? WHERE project_id = ?", code, projectId);
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update project code");
        }
	}

	@Override
	public Project getProjectById(int projectId) {
		try {
			List<Project> project = jdbcTemplate.query("SELECT * FROM project WHERE project_id = ?", projectRowMapper, projectId);
			
			if (project.isEmpty()) {
				return null;
			} else {
				return project.get(0);
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get project by id");
        }
	}

	@Override
	public Project getProjectByCode(String code) {
		try {
			List<Project> project = jdbcTemplate.query("SELECT * FROM project WHERE code = ?", projectRowMapper, code);
			
			if (project.isEmpty()) {
				return null;
			} else {
				return project.get(0);
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get project by code");
        }
	}
}
