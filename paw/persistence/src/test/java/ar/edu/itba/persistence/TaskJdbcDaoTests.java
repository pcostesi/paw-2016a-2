package ar.edu.itba.persistence;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskJdbcDaoTests {

	private TaskDao taskDao;
	@Autowired
	private StoryDao storyDao;
	@Autowired
	private IterationDao iterDao;
	@Autowired
	private ProjectDao projectDao;
	private UserDao userDao;
	private String projectName = "TesterProject";
	private Story testStory;
	
	@Autowired
	DataSource ds;

	@Before
	public void setUp() throws Exception {
		Project testProject = projectDao.createProject(projectName, "Best Project EVAR", "Test");
		LocalDate beginDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(15);
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
