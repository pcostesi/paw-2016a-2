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
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectStatus;
import ar.edu.itba.persistence.rowmapping.IterationDetailRowMapper;
import ar.edu.itba.persistence.rowmapping.ProjectDetailRowMapper;

@Repository
public class ProjectJdbcDao implements ProjectDao{
	
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private ProjectDetailRowMapper projectDetailRowMapper;
    private IterationDetailRowMapper iterationDetailRowMapper;

    @Autowired
    public ProjectJdbcDao(final DataSource ds) {
    		projectDetailRowMapper = new ProjectDetailRowMapper();
    		iterationDetailRowMapper = new IterationDetailRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("project").usingGeneratedKeyColumns("project_id");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS project ("
                            + "project_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                            + "name varchar(100) NOT NULL,"
                            + "code varchar(10) NOT NULL,"
                            + "description varchar(500) NOT NULL,"
                            + "date_start DATE NOT NULL,"
                            + "status INTEGER NOT NULL,"
                            + "PRIMARY KEY ( project_id, name ),"
                            + "UNIQUE(name),"
                            + "UNIQUE(code)"
                    + ")");
    }

	@Override
	public Project createProject(final String name, final String description) {
		
		
		Integer projectCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE name = ?", Integer.class, name);
		if (projectCount > 0) {
			throw new IllegalStateException("Project doesnt exist");
		}
		
		Date curDate = new Date();
		Map<String, Object> args = new HashMap<String, Object>();
        args.put("name", name);
        args.put("description", description);
        args.put("date_start", new java.sql.Date(new Date().getTime()));
        args.put("status", ProjectStatus.OPEN.getValue());
        int projectId = jdbcInsert.executeAndReturnKey(args).intValue();

        return new Project(projectId, name, description, curDate, ProjectStatus.OPEN);
	}
	
	@Override
	public boolean deleteProject(int projectId) {
		List<Iteration> projectIterations = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationDetailRowMapper, projectId);
		
		if (projectIterations.isEmpty()) {
			return false;
		}
		
		for (Iteration iteration: projectIterations){
			int itId = iteration.getIterationId();
			jdbcTemplate.update("DELETE FROM task WHERE iteration_id = ?", itId);
			jdbcTemplate.update("DELETE FROM log WHERE iteration_id = ?", itId);
			jdbcTemplate.update("DELETE FROM iteration WHERE iteration_id = ?", itId);
			// TODO falta updetear los numbers de las iteraciones en la tabla
		}
		
		return jdbcTemplate.update("DELETE FROM project WHERE project_id = ?", projectId) > 0;
	}
	
	@Override
	public List<Project> getProjectDetailList() {
        return jdbcTemplate.query("SELECT * FROM project", projectDetailRowMapper);
	}
	
	@Override
	public Project getProjectWithDetails(String projectName) {
	  if (projectName == null || projectName.length() == 0) {
		  throw new IllegalArgumentException("Illegal project name");
	  }

      List<Project> resultRows = jdbcTemplate.query("SELECT * FROM project WHERE name = ?", projectDetailRowMapper, projectName);
      
      if (resultRows.isEmpty()) {
              throw new IllegalStateException("Project doesnt exist");
      }

      Project requestedProject = new Project(resultRows.get(0));
      
      List<Iteration> iterationDetailRows = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationDetailRowMapper, requestedProject.getProjectDetails().getProjectId());
      
      for (Iteration itDetail: iterationDetailRows){
    	  requestedProject.addIteration(itDetail);
      }
      
      return requestedProject;
	}

	@Override
	public Project getProjectWithDetails(int projectId) {
		List<Project> resultRows = jdbcTemplate.query("SELECT * FROM project WHERE project_id = ?", projectDetailRowMapper, projectId);
	      
	      if (resultRows.isEmpty()) {
	              throw new IllegalStateException("Project doesnt exist");
	      }

	      Project requestedProject = new Project(resultRows.get(0));
	      
	      List<Iteration> iterationDetailRows = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationDetailRowMapper, requestedProject.getProjectDetails().getProjectId());
	      
	      for (Iteration itDetail: iterationDetailRows){
	    	  requestedProject.addIteration(itDetail);
	      }
	      
	      return requestedProject;
	}
}
