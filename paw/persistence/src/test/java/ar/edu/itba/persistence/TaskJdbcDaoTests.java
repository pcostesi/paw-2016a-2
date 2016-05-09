package ar.edu.itba.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;


@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class TaskJdbcDaoTests {

	private TaskDao taskDao;
	private StoryDao storyDao;
	private IterationDao iterDao;
	private ProjectDao projectDao;
	private UserDao userDao;
	private String projectName = "TesterProject";
	private Story testStory;
	
	@Autowired
	DataSource ds;

	@Before
	public void setUp() throws Exception {

		taskDao = new TaskJdbcDao(ds);
		storyDao = new StoryJdbcDao(ds);
		iterDao = new IterationJdbcDao(ds);
		projectDao = new ProjectJdbcDao(ds);
		userDao = new UserJdbcDao(ds);
		Project testProject = projectDao.createProject(projectName, "Best Project EVAR", "Test");
		Date beginDate = new Date();
		Date endDate = new Date();
		Iteration testIteration = iterDao.createIteration(testProject.projectId(), 1, beginDate, endDate);
		testStory = storyDao.createStory(testIteration.iterationId(),
				"A sad story about extreme unhappyness whilst testing");
	}

	@Test
	public void CreateTaskWithCorrectParametersTest() {
	//	Task newTask = dao.createTask(testStory.getStoryId(), "Test Task", "The tester's life is a tough one");
	//	assertNotNull(newTask);

	}

	
	public void createTaskWithWrongParameters() {
	//	Task newTask = dao.createTask(testStory.getStoryId(), "Tasdasdk", "The tester's life is a tough one");
	//	assertNull(newTask);
	}

}
