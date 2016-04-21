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

            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS iteration ("
            				+ "iteration_id INTEGER NOT NULL IDENTITY,"
                            + "project_id INTEGER NOT NULL,"
                            + "number INTEGER NOT NULL,"
                            + "date_start DATE NOT NULL,"
                            + "date_end DATE NOT NULL,"
                            + "FOREIGN KEY ( project_id ) REFERENCES project ( project_id ) ON DELETE CASCADE,"
                            + "UNIQUE ( project_id, number )"
                    + ")");
    }
	
	@Override
	public void deleteIteration(int iterationId) {
		jdbcTemplate.update("DELETE FROM iteration WHERE iteration_id = ?", iterationId);
	}

	@Override
	public int getNextIterationNumber(int projectId) {
		Integer itNumber = jdbcTemplate.queryForObject("SELECT MAX(number) FROM iteration WHERE project_id = ?", Integer.class, projectId);
		if (itNumber == null) {
			return 1;
		} else {
			return itNumber+1;
		}
	}

	@Override
	public Iteration createIteration(int projectId, int nextIterationNumber, Date beginDate, Date endDate) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("project_id", projectId);
        args.put("number", nextIterationNumber);
        args.put("date_start", new java.sql.Date(beginDate.getTime()));
        args.put("date_end", new java.sql.Date(endDate.getTime()));
		
        int iterationId = jdbcInsert.executeAndReturnKey(args).intValue();
        
		return new Iteration(iterationId, nextIterationNumber, beginDate, endDate);
		
	}

	@Override
	public Iteration getIteration(int projectId, int iterationNumber) {
		List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ? AND number = ?", iterationDetailRowMapper, projectId, iterationNumber);

		if (iterationList.isEmpty()) {
			return null;
		} else {
			return iterationList.get(0);
		}
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		List<Iteration> iterationList = jdbcTemplate.query("SELECT * FROM iteration WHERE iteration_id = ?", iterationDetailRowMapper, iterationId);

		if (iterationList.isEmpty()) {
			return null;
		} else {
			return iterationList.get(0);
		}
	}

	@Override
	public boolean iterationExists(int iterationId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iteration WHERE iteration_id = ?", Integer.class, iterationId) == 1;
	}

	@Override
	public void updateBeginDate(int iterationId, Date beginDate) {
		jdbcTemplate.update("UPDATE iteration SET date_start = ? WHERE iteration_id = ?", new java.sql.Date(beginDate.getTime()));
	}

	@Override
	public void updateEndDate(int iterationId, Date endDate) {
		jdbcTemplate.update("UPDATE iteration SET date_start = ? WHERE iteration_id = ?", new java.sql.Date(endDate.getTime()));
	}

	@Override
	public List<Iteration> getIterationsForProject(int projectId) {
		return jdbcTemplate.query("SELECT * FROM iteration WHERE project_id = ?", iterationDetailRowMapper, projectId);
	}

	@Override
	public void updateNumbersAfterDelete(int number) {
		jdbcTemplate.update("UPDATE iteration SET number = (number-1) WHERE number > ?", number);
	}
}
