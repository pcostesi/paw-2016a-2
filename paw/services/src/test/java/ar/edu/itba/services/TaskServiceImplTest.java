package ar.edu.itba.services;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskServiceImplTest {
	private static final int taskID = 0;

	private TaskService ts;

	@Mock
	private TaskDao dao;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ts = new TaskServiceImpl(dao);
	}

	@Test
	public void testDeleteTask() {	
		ts.deleteTask(taskID);
		verify(dao, atLeastOnce()).deleteTask(taskID);
	}
	
	@Test
	public void testCreateTask(){
		ts.createTask(10, "Epic tests", "Testing tester");
		verify(dao, atLeastOnce()).createTask(10, "Epic tests", "Testing tester");
	}
	
	@Test
	public void createTaskInProject(String projectName, int iterationNumber, String title, String description){
		
	}
	
	@Test
	public void getTaskTest(int taskId){
		
	}
	
	@Test
	public void deleteTask(int taskId){
		
	}
	
	@Test
	public void changeOwnership(int taskId, User user){
		
	}
	
	@Test
	public void changeStatus(int taskId, TaskStatus status){
		
	}

}