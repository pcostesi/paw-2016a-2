package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
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

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Iteration;
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
	
	@Mock
	private Iteration testIteration;

	private final Status status = Status.NOT_STARTED;
	private final Score score = Score.EASY;
	private final Priority priority = Priority.NORMAL;
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
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ts = new TaskServiceImpl(taskDao, storyDao);
	}

	@Test
	public void testDeleteTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
		Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask);
	}

	@Test
	public void CreateTaskWithValidParameters() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Mockito.when(testUser.username()).thenReturn("A user name");
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
		Task newTask = ts.createTask(testStory, name, description, status, testUser, score, priority);
		verify(taskDao, atLeastOnce()).createTask(testStory, name, description, status, testUser, score, priority);
		assertNotNull(newTask);
	}

	@Test(expected = Exception.class)
	public void createTaskWithInfiniteName() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Task newTask = ts.createTask(testStory, veryLongString, description, status, testUser, score, priority);
		assertNull(newTask);
	}

	@Test(expected = IllegalStateException.class)
	public void createTaskWithInexistentStory() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(testUser.username()).thenReturn("A user name");
		ts.createTask(testStory, name, description, status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithInexistenStringName() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		ts.createTask(testStory, "", description, status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithInexistenStringDescription() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(testUser.username()).thenReturn("A user name");
		ts.createTask(testStory, name, "", status, testUser, score, priority);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTaskWithNullString() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ts.createTask(testStory, null, null, status, testUser, score, priority);
	}

	@Test(expected = IllegalStateException.class)
	public void createExistingTask() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(taskDao.createTask(testStory, name, description, status, testUser, score, priority))
				.thenReturn(testTask);
		Mockito.when(taskDao.taskExists(testStory, name)).thenReturn(true);
		Mockito.when(testUser.username()).thenReturn("A user name");

		Task newTask = ts.createTask(testStory, name, description, status, testUser, score, priority);
		verify(taskDao, atLeastOnce()).createTask(testStory, name, description, status, testUser, score, priority);
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
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
		Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
		ts.deleteTask(testTask);
		verify(taskDao, atLeastOnce()).deleteTask(testTask);
	}

	@Test(expected = IllegalStateException.class)
	public void deleteNonExistingTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
		ts.deleteTask(testTask);
		verify(taskDao, never()).deleteTask(testTask);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNullTask() {
		ts.deleteTask(null);
		verify(taskDao, never()).deleteTask(testTask);
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeStatusWithNullTask() {
		ts.changeStatus(null, status);
	}

	@Test(expected = IllegalStateException.class)
	public void changeOwnerWithFakeTask() {
		Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);

		ts.changeOwnership(testTask, testUser);
		verify(taskDao, times(0)).updateOwner(testTask, testUser);
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeOwnerWithNullTask() {
		ts.changeOwnership(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changePriorityToNull() {
		ts.changePriority(testTask, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changePriorityToNullTask() {
		ts.changePriority(null, Priority.CRITICAL);
	}

	@Test
	public void testGetTaskForStoryWithCorrectParams() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		List<Task> list = ts.getTasksForStory(testStory);
		assertNotNull(list);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetTaskForStoryWithFakeStory() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ts.getTasksForStory(testStory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTaskForStoryWithNullStory() {
		ts.getTasksForStory(null);
	}

	@Test
	public void changeOwnershipToCompletedTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeOwnershipSuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeStatusToNull() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeStatusToInexistentTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeStatusToCompletedTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeStatusSuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void changePriorityToInexistentTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changePriorirtyToCompleteTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changePrioritySuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeScoreToNullTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeScoreToNull() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeScoreToInexistentTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeScoreToCompletedTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeScoreSuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToNullTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToEmpty() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToLongTitle() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToInexistentTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToUsedTitle() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleToCompletedTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeTaskTitleSuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeDescriptionToNull() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeDescriptionToWayTooLongString() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeDescriptionToInexistentTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeDescriptionToCompletedTask() {
		fail("Test not implemented yet");
	}

	@Test
	public void changeDescriptionSuccesfully() {
		fail("Test not implemented yet");
	}

	@Test
	public void verifyTaskNameIsUsedForNullTitle() {
		fail("Test not implemented yet");
	}

	@Test
	public void verifyTaskNameForInexistentStory() {
		fail("Test not implemented yet");
	}

	@Test
	public void verifyTaskNameForNullStory() {
		fail("Test not implemented yet");
	}

	@Test
	public void verifyTaskNameIsUsedSuccesfully() {
		fail("Test not implemented yet");
	}

}