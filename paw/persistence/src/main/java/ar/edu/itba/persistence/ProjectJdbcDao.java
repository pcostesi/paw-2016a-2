package ar.edu.itba.persistence;

import java.sql.Date;
import java.time.LocalDate;
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
import ar.edu.itba.models.PersistableProject;
import ar.edu.itba.models.Project;
import ar.edu.itba.persistence.rowmapping.ProjectRowMapper;

@Repository
public class ProjectJdbcDao implements ProjectDao{
	
	private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final ProjectRowMapper projectRowMapper;

    @Autowired
    public ProjectJdbcDao(final DataSource ds) {
    		projectRowMapper = new ProjectRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("project").usingGeneratedKeyColumns("project_id");
    }

	@Override
	public Project createProject(String name, String description, String code) {
		
		LocalDate curDate = LocalDate.now();
		Map<String, Object> args = new HashMap<String, Object>();
		
        args.put("name", name);
        args.put("description", description);
        args.put("code", code);
        args.put("date_start", Date.valueOf(curDate));
        
        try {
	        int projectId = jdbcInsert.executeAndReturnKey(args).intValue();	
	        return PersistableProject.builder()
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
	public void deleteProject(Project project) {
		try {
			jdbcTemplate.update("DELETE FROM project WHERE project_id = ?", project.projectId());
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
	public boolean projectExists(Project project) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE project_id = ?", Integer.class, project.projectId()) == 1;
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
	public Project updateName(Project project, String name) {
		try {
			PersistableProject persistableProject = (PersistableProject) project;
			persistableProject.setName(name);
			jdbcTemplate.update("UPDATE project SET name = ? WHERE project_id = ?", name, project.projectId());
			return persistableProject;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update project name");
        }
	}

	@Override
	public Project updateDescription(Project project, String description) {
		try {
			PersistableProject persistableProject = (PersistableProject) project;
			persistableProject.setDescription(description);
			jdbcTemplate.update("UPDATE project SET description = ? WHERE project_id = ?", description, project.projectId());
			return persistableProject;
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to update project description");
        }
	}

	@Override
	public Project updateCode(Project project, String code) {
		try {
			PersistableProject persistableProject = (PersistableProject) project;
			persistableProject.setCode(code);
			jdbcTemplate.update("UPDATE project SET code = ? WHERE project_id = ?", code, project.projectId());
			return persistableProject;
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
