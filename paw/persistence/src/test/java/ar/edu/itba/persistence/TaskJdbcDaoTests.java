package ar.edu.itba.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskJdbcDaoTests {

	JdbcTestUtils utils = new JdbcTestUtils();
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private StoryDao storyDao;
	@Autowired
	private IterationDao iterDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	DataSource ds;

	private String pName = "TesterProject";
	private String pCode = "Test";
	private String tName = "Test Task";
	private String tDesc = "The tester's life is a tough one";
	private Story testStory;
	private Iteration testIteration;
	private Project testProject;
	private Task testTask;
	private TaskStatus status = TaskStatus.getByValue(1);
	private TaskScore score = TaskScore.getByValue(1);
	private User owner;
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		testProject = projectDao.createProject(pName, "Best Project EVAR", pCode);
		LocalDate beginDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(15);
		testIteration = iterDao.createIteration(testProject.projectId(),
				iterDao.getNextIterationNumber(testProject.projectId()), beginDate, endDate);
		testStory = storyDao.createStory(testIteration.iterationId(),
				"A sad story about extreme unhappyness while testing");
		if (!userDao.userNameExists("testuser")) {
			owner = userDao.createUser("testuser", "test", "testerr@gmail.com");
		}
		jdbcTemplate = new JdbcTemplate(ds);

	}

	@After
	public void after() {
		projectDao.deleteProject(testProject.projectId());
	}

	@Test
	public void CreateTaskWithCorrectParametersTest() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		assertTrue(utils.countRowsInTable(jdbcTemplate, "task") == 1);
	}

	@Test
	public void getTest() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		testTask = taskDao.getTaskById(testTask.taskId());
		assertTrue(testTask.score() == score);
		assertTrue(testTask.status() == status);
		assertTrue(testTask.title().compareTo(tName) == 0);
		assertTrue(testTask.description().isPresent());
		assertNotNull(testTask);
	}

	@Test(expected = IllegalStateException.class)
	public void createCreatedTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		assertTrue(utils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullName() {
		testTask = taskDao.createTask(testStory.storyId(), null, tDesc, status, owner, score);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithWrongStory() {
		testTask = taskDao.createTask(testStory.storyId() + 1, tName, tDesc, status, owner, score);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithnegativeStory() {
		testTask = taskDao.createTask(-10, tName, tDesc, status, owner, score);
	}

	@Test
	public void deleteTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.deleteTask(testTask.taskId());
		assertTrue(utils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	public void deleteDeletedTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.deleteTask(testTask.taskId());
		taskDao.deleteTask(testTask.taskId());
		assertTrue(utils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	public void deleteTaskWithWrongId() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.deleteTask(testTask.taskId() + 2);
	}

	@Test
	public void updateOwnerTest() {
		String newName = "newname";
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		User newUser = userDao.createUser(newName, "pw", newName + "@test.com");
		taskDao.updateOwner(testTask.taskId(), newName);
		assertTrue(utils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND owner = \'" + newName + "\'") == 1);
	}

	@Test
	public void updateOwnerWithNull() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updateOwner(testTask.taskId(), null);
		assertFalse(taskDao.getTaskById(testTask.taskId()).owner().isPresent());
	}

	@Test
	public void updateStatusTest() {
		int newStatus = 3;
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updateStatus(testTask.taskId(), newStatus);
		assertTrue(utils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND status = " + newStatus) == 1);
	}

	@Test
	public void theMassiveCreator() {
		int i;
		for (i = 0; i < 150; i++) {
			testTask = taskDao.createTask(testStory.storyId(), tName + String.valueOf(i), tDesc + String.valueOf(i),
					status, owner, score);
		}
		assertTrue(utils.countRowsInTable(jdbcTemplate, "task") == 150);
	}

	@Test
	public void taskExistsTest() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		assertTrue(taskDao.taskExists(testTask.taskId()));
	}

	@Test
	public void taskExistsNOT() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.deleteTask(testTask.taskId());
		assertFalse(taskDao.taskExists(testTask.taskId()));
	}

	@Test
	public void updatePriorityTest() {
		int priority = 3;
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updatePriority(testTask.taskId(), priority);
		assertTrue(utils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND priority = " + priority) == 1);
	}

	@Test
	public void getParentTest() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		assertTrue(taskDao.getParentId(testTask.taskId()) == testStory.storyId());
	}
	
	@Test
	public void updateTitleTest(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updateTitle(testTask.taskId(), newTitle);
		assertTrue(utils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newTitle + "\'") == 1);
	}

	@Test(expected = IllegalStateException.class)
	public void updateTitleToExistingTitle(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory.storyId(), newTitle, tDesc, status, owner, score);
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updateTitle(testTask.taskId(), newTitle);
	}
	
	@Test
	public void updateDescriptionTest(){
		String newDesc = "epic description";
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score);
		taskDao.updateTitle(testTask.taskId(), newDesc);
		assertTrue(utils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newDesc + "\'") == 1);
	}
}
