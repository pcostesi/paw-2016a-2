package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.TaskService;
import ar.edu.itba.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskServiceImplTest {

    private final static Status status = Status.NOT_STARTED;
    private final static Score score = Score.EASY;
    private final static Priority priority = Priority.NORMAL;
    private final static String name = "Epic tests";
    private final static String description = "The life of a tester is hard";
    private final static String veryLongString = "jsdjdfklsjdflksjdfklsdjf,mxc bsiG O3 gO8723G OGBo*gB8g o8&g 82G 284 g64 "
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

    // XXX: FIX THIS
    /*
    @Test
    public void createTaskWithValidParameters() {
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
    */

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

    @Test(expected = IllegalStateException.class)
    public void changeOwnershipToCompletedTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        ts.changeOwnership(testTask, testUser);
    }

    @Test
    public void changeOwnershipSuccesfully() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
        Mockito.when(testTask.status()).thenReturn(Status.NOT_STARTED);
        ts.changeStatus(testTask, Status.STARTED);
        verify(taskDao, times(1)).updateStatus(testTask, Status.STARTED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeStatusToNull() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
        Mockito.when(testTask.status()).thenReturn(Status.NOT_STARTED);
        ts.changeStatus(testTask, null);
        verify(taskDao, times(1)).updateStatus(testTask, null);
    }

    @Test(expected = IllegalStateException.class)
    public void changeStatusToInexistentTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
        ts.changeStatus(testTask, Status.STARTED);
    }

    @Test(expected = IllegalStateException.class)
    public void changeStatusToCompletedTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testTask.status()).thenReturn(Status.STARTED);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        ts.changeStatus(testTask, Status.COMPLETED);
    }

    @Test
    public void changeStatusSuccesfully() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
        Mockito.when(testTask.status()).thenReturn(Status.NOT_STARTED);
        ts.changeStatus(testTask, Status.STARTED);
        verify(taskDao, times(1)).updateStatus(testTask, Status.STARTED);
    }

    @Test(expected = IllegalStateException.class)
    public void changePriorityToInexistentTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
        ts.changePriority(testTask, Priority.CRITICAL);
    }

    @Test(expected = IllegalStateException.class)
    public void changePriorirtyToCompleteTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.priority()).thenReturn(Priority.NORMAL);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        ts.changePriority(testTask, Priority.CRITICAL);
    }

    @Test
    public void changePrioritySuccesfully() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.priority()).thenReturn(Priority.NORMAL);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
        ts.changePriority(testTask, Priority.CRITICAL);
        verify(taskDao, times(1)).updatePriority(testTask, Priority.CRITICAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeScoreToNullTask() {
        ts.changeScore(null, Score.VERY_EASY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeScoreToNull() {
        ts.changeScore(testTask, null);
    }

    @Test(expected = IllegalStateException.class)
    public void changeScoreToInexistentTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
        ts.changeScore(testTask, Score.EPIC);
    }

    @Test(expected = IllegalStateException.class)
    public void changeScoreToCompletedTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.score()).thenReturn(score);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        ts.changeScore(testTask, Score.EPIC);
    }

    // XXX: FIX THIS
    /*
    @Test
    public void changeScoreSuccesfully() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.score()).thenReturn(score);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.STARTED);
        ts.changeScore(testTask, Score.EPIC);
        verify(taskDao, times(1)).updateScore(testTask, Score.EPIC);
    }
    */

    @Test(expected = IllegalArgumentException.class)
    public void changeTaskTitleToNullTask() {
        ts.changeTitle(null, "Hola");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTaskTitleToEmpty() {
        ts.changeTitle(testTask, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTaskTitleToLongTitle() {
        ts.changeTitle(testTask, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test(expected = IllegalStateException.class)
    public void changeTaskTitleToInexistentTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
        ts.changeTitle(testTask, "Hello");
    }

    @Test(expected = IllegalStateException.class)
    public void changeTaskTitleToUsedTitle() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.title()).thenReturn("Tituleeeeeh");
        Mockito.when(taskDao.taskExists(taskDao.getParent(testTask), "Titulo usado")).thenReturn(true);
        ts.changeTitle(testTask, "Titulo usado");
    }

    @Test(expected = IllegalStateException.class)
    public void changeTaskTitleToCompletedTask() {
        String newTitle = "Holas";
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.title()).thenReturn("Tituleeeeeh");
        Mockito.when(taskDao.taskExists(taskDao.getParent(testTask), newTitle)).thenReturn(false);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        ts.changeTitle(testTask, newTitle);
    }

    @Test
    public void changeTaskTitleSuccesfully() {
        String newTitle = "Holas";
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.title()).thenReturn("Tituleeeeeh");
        Mockito.when(taskDao.taskExists(taskDao.getParent(testTask), newTitle)).thenReturn(false);
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.STARTED);
        ts.changeTitle(testTask, newTitle);
        verify(taskDao, times(1)).updateTitle(testTask, newTitle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeDescriptionToNullTask() {
        ts.changeDescription(null, "New description");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeDescriptionToWayTooLongString() {
        ts.changeDescription(testTask, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );
    }

    @Test(expected = IllegalStateException.class)
    public void changeDescriptionToInexistentTask() {
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(false);
        ts.changeDescription(testTask, "New description");
    }

    @Test(expected = IllegalStateException.class)
    public void changeDescriptionToCompletedTask() {
        String newDescription = "New description";
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.description()).thenReturn(Optional.of("Old description"));
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
        ts.changeDescription(testTask, newDescription);
    }

    @Test
    public void changeDescriptionSuccesfully() {
        String newDescription = "New description";
        Mockito.when(taskDao.taskExists(testTask)).thenReturn(true);
        Mockito.when(testTask.description()).thenReturn(Optional.of("Old description"));
        Mockito.when(taskDao.getParent(testTask)).thenReturn(testStory);
        Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
        Mockito.when(testIteration.status()).thenReturn(Status.NOT_STARTED);
        ts.changeDescription(testTask, newDescription);
        verify(taskDao, times(1)).updateDescription(testTask, newDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyTaskNameIsUsedForNullTitle() {
        Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
        ts.taskNameExists(testStory, null);
    }

    @Test(expected = IllegalStateException.class)
    public void verifyTaskNameForInexistentStory() {
        Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
        ts.taskNameExists(testStory, "Titulin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyTaskNameForNullStory() {
        ts.taskNameExists(null, "Titulin");
    }

    @Test
    public void verifyTaskNameIsUsedSuccesfully() {
        Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
        ts.taskNameExists(testStory, "Titulo");
        verify(taskDao, times(1)).taskExists(testStory, "Titulo");
    }

}