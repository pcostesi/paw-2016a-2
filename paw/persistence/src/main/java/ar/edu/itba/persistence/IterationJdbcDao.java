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

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Task;
import ar.edu.itba.persistence.rowmapping.IterationDetailRowMapper;
import ar.edu.itba.persistence.rowmapping.ProjectDetailRowMapper;
import ar.edu.itba.persistence.rowmapping.TaskUserRowMapper;

@Repository
public class IterationJdbcDao implements IterationDao {

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private IterationDetailRowMapper iterationDetailRowMapper;
    private TaskUserRowMapper taskUserRowMapper;
    
    @Autowired
    public IterationJdbcDao(final DataSource ds) {
    		iterationDetailRowMapper = new IterationDetailRowMapper();
    		taskUserRowMapper = new TaskUserRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("iteration").usingGeneratedKeyColumns("iteration_id");

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS iteration ("
            				+ "iteration_id INTEGER NOT NULL IDENTITY,"
                            + "project_id INTEGER NOT NULL,"
                            + "number INTEGER NOT NULL,"
                            + "date_start DATE NOT NULL,"
                            + "date_end DATE NOT NULL"
                    + ")");
    }
    
	@Override
	public Iteration createIteration(String projectName, Date beginDate, Date endDate) {
		if (projectName == null || projectName.length() == 0) {
			throw new IllegalArgumentException("Illegal project name");
		}
		
		if (beginDate == null) {
			throw new IllegalArgumentException("Illegal begin date");
		}
		
		if (endDate == null) {
			throw new IllegalArgumentException("Illegal end date");
		}
		
		List<Project> project = jdbcTemplate.query("SELECT * FROM project WHERE name = ?", new ProjectDetailRowMapper(), projectName);
		if (project.isEmpty()) {
			throw new IllegalStateException("Project doesnt exist");
		}

		int projectId = project.get(0).getProjectId();
		
		Integer itNumber = jdbcTemplate.queryForObject("SELECT MAX(number) FROM iteration WHERE project_id = ?", Integer.class, projectId);
		if (itNumber == null) {
			itNumber = 1;
		}else {
			itNumber++;
		}
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("project_id", projectId);
        args.put("number", itNumber);
        args.put("date_start", new java.sql.Date(beginDate.getTime()));
        args.put("date_end", new java.sql.Date(endDate.getTime()));
		
        int iterationId = jdbcInsert.executeAndReturnKey(args).intValue();
        
		return new Iteration(iterationId, itNumber, beginDate, endDate);
	}
	
	@Override
	public boolean deleteIteration(int iterationId) {
		boolean hasEntriesToDelete = jdbcTemplate.update("DELETE FROM iteration WHERE iteration_id = ?", iterationId) > 0;
		
		if (hasEntriesToDelete) {
			jdbcTemplate.update("DELETE FROM task WHERE iteration_id = ?", iterationId);
			
			// TODO falta updetear los numbers de las iteraciones en la tabla y borrar log cuando haya
			//jdbcTemplate.update("DELETE FROM log WHERE iteration_id = ?", iterationId);			
		}		

		return hasEntriesToDelete;
	}

	@Override
	public Iteration getIteration(String projectName, int iterationNumber){
		if ( projectName == null || projectName.length() == 0 ) {
			throw new IllegalArgumentException("Illegal project name");
		}
		
		if ( iterationNumber < 1 ) {
			throw new IllegalArgumentException("Illegal iteration number");
		}
		
		boolean projectExists = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE name = ?", Integer.class, projectName) > 0;
		if(!projectExists) {
			throw new IllegalStateException("Project doesnt exist");
		}		
		Integer projectId = jdbcTemplate.queryForObject("SELECT project_id FROM project WHERE name = ?", Integer.class, projectName);

		boolean iterationExists = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iteration WHERE project_id= ? AND number = ?", Integer.class, projectId, iterationNumber) > 0;
		if (!iterationExists) {
			throw new IllegalStateException("Iteration doesnt exist");
		}
		Integer iterationId = jdbcTemplate.queryForObject("SELECT iteration_id FROM iteration WHERE project_id= ? AND number = ?", Integer.class, projectId, iterationNumber);
		
		return getIteration(iterationId);
	}

	@Override
	public Iteration getIteration(int iterationId){
		List<Iteration> detailList = jdbcTemplate.query("SELECT * FROM iteration WHERE iteration_id = ?", iterationDetailRowMapper, iterationId);
		
		if (detailList.isEmpty()) {
			throw new IllegalStateException("Iteration doesnt exist");
		}
		
		Iteration requestedIteration = new Iteration(detailList.get(0));
		
		List<Task> taskList = jdbcTemplate.query("SELECT * FROM task LEFT JOIN user ON user.username = task.owner WHERE iteration_id = ?", taskUserRowMapper, iterationId);

		for (Task task: taskList) {
			requestedIteration.addTask(task);
		}
		
		// TODO Falta agregarle los logs
		
		return requestedIteration;
	}
}
