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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskJdbcDaoTests {
	
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

	private final String pName = "TesterProject";
	private final String pCode = "Test";
	private final String tName = "Test Task";
	private final String tDesc = "The tester's life is a tough one";
	private final Status status = Status.NOT_STARTED;
	private final Score score = Score.EASY;
	private final Priority priority = Priority.NORMAL;
	
	private JdbcTemplate jdbcTemplate;
	private Story testStory;
	private Iteration testIteration;
	private Project testProject;
	private Task testTask;
	private User owner;

	@Before
	public void setUp() throws Exception {
		if (userDao.userNameExists("testuser")) {
			owner = userDao.getByUsername("testuser");
		} else {
			owner = userDao.createUser("testuser", "test", "testerr@gmail.com");			
		}
		jdbcTemplate = new JdbcTemplate(ds);
		testProject = projectDao.createProject(owner, pName, "Best Project EVAR", pCode);
		LocalDate beginDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(15);
		testIteration = iterDao.createIteration(testProject, 1, beginDate, endDate);
		testStory = storyDao.createStory(testIteration,
				"A sad story about extreme unhappyness while testing");
	}

	@After
	public void after() {
		projectDao.deleteProject(testProject);
	}

	@Test
	public void CreateTaskWithCorrectParametersTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 1);
	}

	@Test
	public void getTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		testTask = taskDao.getTaskById(testTask.taskId());
		assertTrue(testTask.score() == score);
		assertTrue(testTask.status() == status);
		assertTrue(testTask.title().compareTo(tName) == 0);
		assertTrue(testTask.description().isPresent());
		assertNotNull(testTask);
	}

	@Test(expected = IllegalStateException.class)
	public void createCreatedTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullName() {
		testTask = taskDao.createTask(testStory, null, tDesc, status, owner, score, priority);
	}

	@Test
	public void deleteTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	public void deleteDeletedTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		taskDao.deleteTask(testTask);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	public void updateOwnerTest() {
		String newName = "newname";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		User newOwner = userDao.createUser(newName, "pw", newName + "@test.com");
		taskDao.updateOwner(testTask, newOwner);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND owner = \'" + newName + "\'") == 1);
	}

	@Test
	public void updateOwnerWithNull() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateOwner(testTask, null);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND owner IS NULL") == 1);
	}

	@Test
	public void updateStatusTest() {
		Status newStatus = Status.COMPLETED;
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateStatus(testTask, newStatus);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND status = " + newStatus.getValue()) == 1);
	}

	@Test
	public void theMassiveCreator() {
		int i;
		for (i = 0; i < 150; i++) {
			testTask = taskDao.createTask(testStory, tName + String.valueOf(i), tDesc + String.valueOf(i),
					status, owner, score, priority);
		}
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 150);
	}

	@Test
	public void taskExistsTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(taskDao.taskExists(testTask));
	}

	@Test
	public void taskExistsNOT() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		assertFalse(taskDao.taskExists(testTask));
	}

	@Test
	public void updatePriorityTest() {
		Priority newPriority = Priority.CRITICAL;
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updatePriority(testTask, newPriority);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND priority = " + newPriority.getValue()) == 1);
	}

	@Test
	public void getParentTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(taskDao.getParent(testTask) == testStory);
	}
	
	@Test
	public void updateTitleTest(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newTitle);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newTitle + "\'") == 1);
	}

	@Test(expected = IllegalStateException.class)
	public void updateTitleToExistingTitle(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory, newTitle, tDesc, status, owner, score, priority);
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newTitle);
	}
	
	@Test
	public void updateDescriptionTest(){
		String newDesc = "epic description";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newDesc);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newDesc + "\'") == 1);
	}
}
