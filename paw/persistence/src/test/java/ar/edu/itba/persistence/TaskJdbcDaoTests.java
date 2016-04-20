package ar.edu.itba.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Task;

public class TaskJdbcDaoTests {

	private TaskDao dao;
	private IterationDao iterDao;
	private ProjectDao projectDao;
	private UserDao userDao;
	private String projectName = "TesterProject";

	@Before
	public void setUp() throws Exception {
		DataSource ds = dataSurce();
		dao = new TaskJdbcDao(ds);
		iterDao = new IterationJdbcDao(ds);
		projectDao = new ProjectJdbcDao(ds);
		projectDao.createProject(projectName, "Best Project EVAR");
		Date beginDate = new Date();
		Date endDate = new Date();
		iterDao.createIteration(projectName, beginDate, endDate);
		userDao = new UserJdbcDao(ds);
		
	}

	@Test
	public void CreateTaskWithCorrectParametersTest() {
		Task newTask = dao.createTask(projectName, 1,  "Test Task", "The tester's life is a tough one"); 
		assertNotNull(newTask);
	}

	@Test
	public void createTaskWithWrongParameters(){
		Task newTask = dao.createTask(projectName, 5, "Test Task", "The tester's life is a tough one");
		assertNull(newTask);
	}
	
	private DataSource dataSurce() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(JDBCDriver.class);
		ds.setUrl("jdbc:hsqldb:mem:paw");
		ds.setUsername("hq");
		ds.setPassword("");
		return ds;
	}

}
