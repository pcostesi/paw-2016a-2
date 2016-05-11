package ar.edu.itba.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

@Sql("classpath:schema.sql")
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

	@Before
	public void setUp() throws Exception {
		testProject = projectDao.createProject(pName, "Best Project EVAR", pCode);
		LocalDate beginDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(15);
		testIteration= iterDao.createIteration(testProject.projectId(), iterDao.getNextIterationNumber(testProject.projectId()), beginDate, endDate);
		testStory = storyDao.createStory(testIteration.iterationId(),
				"A sad story about extreme unhappyness while testing");
		if(owner == null){
			//System.out.println(owner == null);
			//owner = userDao.createUser("t", "test", "testerr@gmail.com");
			//System.out.println(owner == null);
		}
		
	}
	
	@After
	public void after(){
		projectDao.deleteProject(testProject.projectId());
	}

	@Test
	public void CreateTaskWithCorrectParametersTest() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		assertTrue(testTask.score() == score);
		assertTrue(testTask.status() == status);
		assertTrue(testTask.title().compareTo(tName) == 0);
		assertTrue(testTask.description().isPresent());
		assertTrue(testTask.owner().isPresent());
		assertNotNull(testTask);
	}

	@Test(expected = IllegalStateException.class)
	public void createCreatedTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullName() {
		testTask = taskDao.createTask(testStory.storyId(), null, tDesc, status, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullDesc() {
		testTask = taskDao.createTask(testStory.storyId(), tName, null, status, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullStatus() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, null, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithNullScore() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, null );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithWrongStory() {
		testTask = taskDao.createTask(testStory.storyId() +1, tName, tDesc, status, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void createTaskWithnegativeStory() {
		testTask = taskDao.createTask(-10, tName, tDesc, status, owner, score );
	}
	
	@Test(expected = IllegalStateException.class)
	public void deleteTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.deleteTask(testTask.taskId());
		taskDao.getTaskById(testTask.taskId());
	}
	
	@Test
	public void deleteDeletedTask() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.deleteTask(testTask.taskId());
		taskDao.deleteTask(testTask.taskId());
	}

	@Test
	public void deleteTaskWithWrongId() {
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.deleteTask(testTask.taskId()+2);
	}
	
	@Test
	public void updateOwnerTest(){
		String newName = "newname";
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.updateOwner(testTask.taskId(), newName);
		assertTrue(newName.compareTo(taskDao.getTaskById(testTask.taskId()).owner().get().username()) == 0);
	}
	
	@Test
	public void updateOwnerWithNull(){
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.updateOwner(testTask.taskId(), null);
		assertFalse(taskDao.getTaskById(testTask.taskId()).owner().isPresent());
	}
	
	@Test
	public void updateStatusTest(){
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.updateStatus(testTask.taskId(), 0);
		assertTrue(taskDao.getTaskById(testTask.taskId()).status().compareTo(TaskStatus.getByValue(0)) == 0);
	}
	
	@Test(expected = IllegalStateException.class)
	public void updateStatusWithInexistentStatus(){
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.updateStatus(testTask.taskId(), 100);
	}
	
	@Test(expected = IllegalStateException.class)
	public void updateStatusWithInexistentTaskId(){
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		taskDao.updateStatus(testTask.taskId()+1000, 100);
	}
	
	@Test
	public void getTaskById(){
		System.out.println(testTask==null);
		testTask = taskDao.createTask(testStory.storyId(), tName, tDesc, status, owner, score );
		System.out.println(testTask==null);
		System.out.println("Task id:" + testTask.taskId());
		System.out.println("grabbed:" + taskDao.getTasksForStory(testStory.storyId()));
		System.out.println(testTask.taskId());
		assertSame(testTask.taskId(), taskDao.getTaskById(13));
	}
	
}
