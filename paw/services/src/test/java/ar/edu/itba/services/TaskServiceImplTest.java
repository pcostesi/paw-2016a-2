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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

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

	private TaskStatus status = TaskStatus.getByValue(1);
	private TaskScore score = TaskScore.getByValue(1);
	private String name = "Epic tests";
	private String description = "The life of a tester is hard";
	private String veryLongString = "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
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
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ts = new TaskServiceImpl(taskDao, storyDao);
	}

	@Test
	public void testDeleteTask() {
		Mockito.when(taskDao.taskExists(testTask.taskId())).thenReturn(true);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask.taskId());
	}

	@Test
	public void CreateTaskWithValidParameters() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory.storyId(), name, description, status, testUser, score))
				.thenReturn(testTask);
		Task newTask = ts.createTask(testStory, name, description, status, testUser, score);
		verify(taskDao, atLeastOnce()).createTask(testStory.storyId(), name, description, status, testUser, score);
		assertNotNull(newTask);
	}

	@Test(expected = Exception.class)
	public void createTaskWithInfiniteName() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory.storyId(), name, description, status, testUser, score))
				.thenReturn(testTask);
		Task newTask = ts.createTask(testStory, veryLongString, description, status, testUser, score);
		assertNull(newTask);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithInexistentStory() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		ts.createTask(testStory, name, description, status, testUser, score);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithInexistenStringName() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		ts.createTask(testStory, "", description, status, testUser, score);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithInexistenStringDescription() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		ts.createTask(testStory, name, "", status, testUser, score);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithNullString() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		ts.createTask(testStory, null, null, status, testUser, score);
	}

	@Test(expected = IllegalStateException.class)
	public void createExistingTask() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory.storyId(), name, description, status, testUser, score))
				.thenReturn(testTask);
		Mockito.when(taskDao.taskExists(testStory.storyId(), name)).thenReturn(true);

		Task newTask = ts.createTask(testStory, name, description, status, testUser, score);
		verify(taskDao, atLeastOnce()).createTask(testStory.storyId(), name, description, status, testUser, score);
		assertNotNull(newTask);
	}

	@Test
	public void getTaskByIdwithCorrect() {
		Mockito.when(taskDao.getTaskById(1)).thenReturn(testTask);

		Task newTask = ts.getTaskById(1);
		verify(taskDao, atLeastOnce()).getTaskById(1);
		assertNotNull(newTask);
	}

	@Test(expected = IllegalStateException.class)
	public void getTaskByIdwithIncorrect() {
		Mockito.when(taskDao.getTaskById(1)).thenReturn(null);
		ts.getTaskById(1);
	}

	@Test
	public void deleteExistingTask() {
		Mockito.when(taskDao.taskExists(testTask.taskId())).thenReturn(true);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask.taskId());
	}

	@Test(expected = IllegalStateException.class)
	public void deleteNonExistingTask() {
		Mockito.when(taskDao.taskExists(testTask.taskId())).thenReturn(false);
		ts.deleteTask(testTask);
		verify(taskDao, never()).deleteTask(testTask.taskId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNullTask() {
		ts.deleteTask(null);
		verify(taskDao, never()).deleteTask(testTask.taskId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeStatusWithNullTask() {
		ts.changeStatus(null, status);
	}

	@Test(expected = IllegalStateException.class)
	public void changeOwnerWithFakeTask() {
		Mockito.when(taskDao.taskExists(testTask.taskId())).thenReturn(false);

		ts.changeOwnership(testTask, testUser);
		verify(taskDao, times(0)).updateOwner(testTask.taskId(), testUser.username());
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeOwnerWithNullTask() {
		ts.changeOwnership(null, null);
	}

	@Test
	public void testGetTaskForStoryWithCorrectParams() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		List<Task> list = ts.getTasksForStory(testStory);
		assertNotNull(list);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetTaskForStoryWithFakeStory() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		ts.getTasksForStory(testStory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTaskForStoryWithNullStory() {
		ts.getTasksForStory(null);
	}

}