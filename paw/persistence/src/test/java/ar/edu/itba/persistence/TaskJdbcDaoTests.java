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
import org.springframework.transaction.annotation.Transactional;

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
	private final Status status = Status.getByValue(1);
	private final Score score = Score.getByValue(1);
	private final Priority priority = Priority.getByValue(1);
	private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
	
	private Story testStory;
	private Iteration testIteration;
	private Project testProject;
	private Task testTask;
	private User owner;

	@Before
	@Transactional
	public void setUp() throws Exception {
		testProject = projectDao.createProject(pName, "Best Project EVAR", pCode);
		LocalDate beginDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(15);
		testIteration = iterDao.createIteration(testProject,
				iterDao.getNextIterationNumber(testProject), beginDate, endDate);
		testStory = storyDao.createStory(testIteration,
				"A sad story about extreme unhappyness while testing");
		if (!userDao.userNameExists("testuser")) {
			owner = userDao.createUser("testuser", "test", "testerr@gmail.com");
		}
	}

	@After
	@Transactional
	public void after() {
		projectDao.deleteProject(testProject);
	}

	@Test
	@Transactional
	public void CreateTaskWithCorrectParametersTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 1);
	}

	@Test
	@Transactional
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
	@Transactional
	public void createCreatedTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void createTaskWithNullName() {
		testTask = taskDao.createTask(testStory, null, tDesc, status, owner, score, priority);
	}

	@Test
	@Transactional
	public void deleteTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	@Transactional
	public void deleteDeletedTask() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		taskDao.deleteTask(testTask);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 0);
	}

	@Test
	@Transactional
	public void updateOwnerTest() {
		String newName = "newname";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		userDao.createUser(newName, "pw", newName + "@test.com");
		taskDao.updateOwner(testTask, owner);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND owner = \'" + newName + "\'") == 1);
	}

	@Test
	@Transactional
	public void updateOwnerWithNull() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateOwner(testTask, null);
		assertFalse(taskDao.getTaskById(testTask.taskId()).owner().isPresent());
	}

	@Test
	@Transactional
	public void updateStatusTest() {
		Status newStatus = Status.COMPLETED;
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateStatus(testTask, newStatus);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND status = " + newStatus) == 1);
	}

	@Test
	@Transactional
	public void theMassiveCreator() {
		int i;
		for (i = 0; i < 150; i++) {
			testTask = taskDao.createTask(testStory, tName + String.valueOf(i), tDesc + String.valueOf(i),
					status, owner, score, priority);
		}
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "task") == 150);
	}

	@Test
	@Transactional
	public void taskExistsTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(taskDao.taskExists(testTask));
	}

	@Test
	@Transactional
	public void taskExistsNOT() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.deleteTask(testTask);
		assertFalse(taskDao.taskExists(testTask));
	}

	@Test
	@Transactional
	public void updatePriorityTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updatePriority(testTask, priority);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND priority = " + priority) == 1);
	}

	@Test
	@Transactional
	public void getParentTest() {
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		assertTrue(taskDao.getParent(testTask) == testStory);
	}
	
	@Test
	@Transactional
	public void updateTitleTest(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newTitle);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newTitle + "\'") == 1);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void updateTitleToExistingTitle(){
		String newTitle = "epic title";
		testTask = taskDao.createTask(testStory, newTitle, tDesc, status, owner, score, priority);
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newTitle);
	}
	
	@Test
	@Transactional
	public void updateDescriptionTest(){
		String newDesc = "epic description";
		testTask = taskDao.createTask(testStory, tName, tDesc, status, owner, score, priority);
		taskDao.updateTitle(testTask, newDesc);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "task",
				"task_id = " + testTask.taskId() + " AND title = \'" + newDesc + "\'") == 1);
	}
}
