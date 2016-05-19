package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskServiceImplTest {

	private TaskService ts;
	
	@Mock
	private Task testTask;
	
	@Mock
	private StoryDao storyDao;
	
	@Mock
	private TaskDao taskDao;
	
	@Mock
	private Story testStory;
	
	@Mock
	private User testUser;

	private final Status status = Status.getByValue(1);
	private final Score score = Score.getByValue(1);
	private final Priority priority = Priority.getByValue(1);
	private final String name = "Epic tests";
	private final String description = "The life of a tester is hard";
	private final String veryLongString = "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
			+ "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 ";

	@Before
	@Transactional
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ts = new TaskServiceImpl(taskDao, storyDao);
	}

	@Test
	@Transactional
	public void testDeleteTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask);
	}

	@Test
	@Transactional
	public void CreateTaskWithValidParameters() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Task newTask = ts.createTask(testStory, name, description, status, testUser, score, priority);
		verify(taskDao, atLeastOnce()).createTask(testStory, name, description, status, testUser, score, priority);
		assertNotNull(newTask);
	}

	@Test(expected = Exception.class)
	@Transactional
	public void createTaskWithInfiniteName() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Task newTask = ts.createTask(testStory, veryLongString, description, status, testUser, score, priority);
		assertNull(newTask);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void createTaskWithInexistentStory() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ts.createTask(testStory, name, description, status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createTaskWithInexistenStringName() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		ts.createTask(testStory, "", description, status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createTaskWithInexistenStringDescription() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		ts.createTask(testStory, name, "", status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createTaskWithNullString() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ts.createTask(testStory, null, null, status, testUser, score, priority);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void createExistingTask() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Mockito.when(taskDao.taskExists(testStory, name)).thenReturn(true);

		Task newTask = ts.createTask(testStory, name, description, status, testUser, score, priority);
		verify(taskDao, atLeastOnce()).createTask(testStory, name, description, status, testUser, score, priority);
		assertNotNull(newTask);
	}

	@Test
	@Transactional
	public void getTaskByIdwithCorrect() {
		Mockito.when(taskDao.getTaskById(1)).thenReturn(testTask);

		Task newTask = ts.getTaskById(1);
		verify(taskDao, atLeastOnce()).getTaskById(1);
		assertNotNull(newTask);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void getTaskByIdwithIncorrect() {
		Mockito.when(taskDao.getTaskById(1)).thenReturn(null);
		ts.getTaskById(1);
	}

	@Test
	@Transactional
	public void deleteExistingTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void deleteNonExistingTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
		ts.deleteTask(testTask);
		verify(taskDao, never()).deleteTask(testTask);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void deleteNullTask() {
		ts.deleteTask(null);
		verify(taskDao, never()).deleteTask(testTask);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void changeStatusWithNullTask() {
		ts.changeStatus(null, status);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void changeOwnerWithFakeTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);

		ts.changeOwnership(testTask, testUser);
		verify(taskDao, times(0)).updateOwner(testTask, testUser);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void changeOwnerWithNullTask() {
		ts.changeOwnership(null, null);
	}

	@Test
	@Transactional
	public void testGetTaskForStoryWithCorrectParams() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		List<? extends Task> list = ts.getTasksForStory(testStory);
		assertNotNull(list);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void testGetTaskForStoryWithFakeStory() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ts.getTasksForStory(testStory);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void testGetTaskForStoryWithNullStory() {
		ts.getTasksForStory(null);
	}

}