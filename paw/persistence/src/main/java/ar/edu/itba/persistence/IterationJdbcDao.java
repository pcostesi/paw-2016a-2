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
import ar.edu.itba.models.ImmutableIteration;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.persistence.rowmapping.IterationRowMapper;

@Repository
public class IterationJdbcDao implements IterationDao {

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private IterationRowMapper iterationDetailRowMapper;
    
    @Autowired
    public IterationJdbcDao(final DataSource ds) {
    		iterationDetailRowMapper = new IterationRowMapper();
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("iteration").usingGeneratedKeyColumns("iteration_id");
    }
	
	@Override
	public void deleteIteration(int iterationId) {
		try {
			jdbcTemplate.update("DELETE FROM iteration WHERE iteration_id = ?", iterationId);
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to delete iteration");
	    }
	}

	@Override
	public int getNextIterationNumber(int projectId) {
		try {
			Integer itNumber = jdbcTemplate.queryForObject("SELECT MAX(number) FROM iteration WHERE project_id = ?", Integer.class, projectId);
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
	public Iteration createIteration(int projectId, int nextIterationNumber, LocalDate beginDate, LocalDate endDate) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("project_id", projectId);
        args.put("number", nextIterationNumber);
        args.put("date_start", Date.valueOf(beginDate));
        args.put("date_end", Date.valueOf(endDate));
		
        try {        
	        int iterationId = jdbcInsert.executeAndReturnKey(args).intValue();	        
			return ImmutableIteration.builder()
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
	public Iteration getIteration(int projectId, int iterationNumber) {		
		try {
			List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ? AND number = ?", iterationDetailRowMapper, projectId, iterationNumber);
	
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
			List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE iteration_id = ?", iterationDetailRowMapper, iterationId);
	
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
	public boolean iterationExists(int iterationId) {
		try {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iteration WHERE iteration_id = ?", Integer.class, iterationId) == 1;
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to check if iteration exists");
	    }
	}

	@Override
	public void updateBeginDate(int iterationId, LocalDate beginDate) {
		try {
			jdbcTemplate.update("UPDATE iteration SET date_start = ? WHERE iteration_id = ?", Date.valueOf(beginDate), iterationId);
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to update begin date");
	    }
	}

	@Override
	public void updateEndDate(int iterationId, LocalDate endDate) {
		try {
			jdbcTemplate.update("UPDATE iteration SET date_end = ? WHERE iteration_id = ?", Date.valueOf(endDate), iterationId);
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to update end date");
	    }
	}

	@Override
	public List<Iteration> getIterationsForProject(int projectId) {
		try {
			return jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationDetailRowMapper, projectId);
		} catch (DataAccessException exception) {
	    	throw new IllegalStateException("Database failed to get iterations for project");
	    }
	}

	@Override
	public void updateNumbersAfterDelete(int projectId, int number) {
		try {
			jdbcTemplate.update("UPDATE iteration SET number = (number-1) WHERE number > ? AND project_id = ?", number, projectId);
		} catch (DataAccessException exception) {
			
	    	throw new IllegalStateException("Database failed to update iterations number");
	    }
	}

	@Override
	public int getParentId(int iterationId) {
		try {
			return jdbcTemplate.queryForObject("SELECT project_id FROM iteration WHERE iteration_id = ?", Integer.class, iterationId);
		} catch (DataAccessException exception) {
			
			throw new IllegalStateException(exception.getMessage());
		}
	}

}
