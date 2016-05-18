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

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.PersistableIteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.persistence.rowmapping.IterationRowMapper;
import ar.edu.itba.persistence.rowmapping.ProjectRowMapper;

@Repository
public class IterationJdbcDao implements IterationDao {

	private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final IterationRowMapper iterationRowMapper;
    private final ProjectRowMapper projectRowMapper;
    
    @Autowired
    public IterationJdbcDao(final DataSource ds) {
    		iterationRowMapper = new IterationRowMapper();
    		projectRowMapper = new ProjectRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("iteration").usingGeneratedKeyColumns("iteration_id");
    }
	
	@Override
	public void deleteIteration(Iteration iteration) {
		try {
			jdbcTemplate.update("DELETE FROM iteration WHERE iteration_id = ?", iteration.iterationId());
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to delete iteration");
	    }
	}

	@Override
	public int getNextIterationNumber(Project project) {
		try {
			Integer itNumber = jdbcTemplate.queryForObject("SELECT MAX(number) FROM iteration WHERE project_id = ?", Integer.class, project.projectId());
			if (itNumber == null) {
				return 1;
			} else {
				return itNumber+1;
			}
		} catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to get next iteration number");
        }
	}

	@Override
	public Iteration createIteration(Project project, int nextIterationNumber, LocalDate beginDate, LocalDate endDate) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("project_id", project.projectId());
        args.put("number", nextIterationNumber);
        args.put("date_start", Date.valueOf(beginDate));
        args.put("date_end", Date.valueOf(endDate));
		
        try {        
	        int iterationId = jdbcInsert.executeAndReturnKey(args).intValue();	        
			return PersistableIteration.builder()
					.iterationId(iterationId)
					.number(nextIterationNumber)
					.startDate(beginDate)
					.endDate(endDate)
					.build();
        } catch (DataAccessException exception) {
        	throw new IllegalStateException("Database failed to create iteration");
        }
        
	}

	@Override
	public Iteration getIteration(Project project, int iterationNumber) {		
		try {
			List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ? AND number = ?", iterationRowMapper, project.projectId(), iterationNumber);
	
			if (iterationList.isEmpty()) {
				return null;
			} else {
				return iterationList.get(0);
			}
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to get iteration");
	    }
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		try {			
			List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE iteration_id = ?", iterationRowMapper, iterationId);
	
			if (iterationList.isEmpty()) {
				return null;
			} else {
				return iterationList.get(0);
			}			
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to get iteration by ID");
	    }
	}

	@Override
	public boolean iterationExists(Iteration iteration) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iteration WHERE iteration_id = ?", Integer.class, iteration.iterationId()) == 1;
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to check if iteration exists");
	    }
	}

	@Override
	public Iteration updateStartDate(Iteration iteration, LocalDate startDate) {
		try {
			PersistableIteration persistableIteration = (PersistableIteration) iteration;
			persistableIteration.setStartDate(startDate);
			jdbcTemplate.update("UPDATE iteration SET date_start = ? WHERE iteration_id = ?", Date.valueOf(startDate), iteration.iterationId());
			return persistableIteration;
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to update begin date");
	    }
	}

	@Override
	public Iteration updateEndDate(Iteration iteration, LocalDate endDate) {
		try {
			PersistableIteration persistableIteration = (PersistableIteration) iteration;
			persistableIteration.setEndDate(endDate);
			jdbcTemplate.update("UPDATE iteration SET date_end = ? WHERE iteration_id = ?", Date.valueOf(endDate), iteration.iterationId());
			return persistableIteration;
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to update end date");
	    }
	}

	@Override
	public List<Iteration> getIterationsForProject(Project project) {
		try {
			return jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationRowMapper, project.projectId());
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to get iterations for project");
	    }
	}

	@Override
	public void updateNumbersAfterDelete(Iteration iteration, int number) {
		try {
			jdbcTemplate.update("UPDATE iteration SET number = (number-1) WHERE number > ? AND project_id = ?", number, iteration.projectId());
		} catch (DataAccessException exception) {
			
	    	throw new IllegalStateException("Database failed to update iterations number");
	    }
	}

	@Override
	public Project getParent(Iteration iteration) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM project WHERE project_id = ?", projectRowMapper, iteration.iterationId());
		} catch (DataAccessException exception) {
			
			throw new IllegalStateException(exception.getMessage());
		}
	}

}
