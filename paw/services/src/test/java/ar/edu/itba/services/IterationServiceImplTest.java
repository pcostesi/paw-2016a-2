package ar.edu.itba.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.interfaces.service.TaskService;
import ar.edu.itba.interfaces.service.UserService;
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
public class IterationServiceImplTest {
	
	@Autowired
	private IterationService is;

	@Autowired
	private ProjectService ps;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private TaskService ts;
	
	@Autowired
	private StoryService ss;

	private Project project;
	private String pName = "IterationTest";
	private String pDesc = "Project for testing Iteration service";
	private String pCode = "ITP";
	private LocalDate beginDate;
	private LocalDate endDate;
	private Iteration iter;

	private User admin;
	private Set<User> members = new HashSet<User>();
	
	@Before
	public void setup() {
		if (us.usernameExists("testName")) {
			admin = us.getByUsername("testName");
		} else {
			admin = us.create("testName", "testPass", "test@mail.com");			
		}
		project = ps.createProject(admin, members, pName, pDesc, pCode);
		endDate = LocalDate.now();
		beginDate = endDate.minusDays(15);
		iter = is.createIteration(project, beginDate, endDate);
	}
	
	@After
	public void endingSetup(){
		if (ps.projectCodeExists(pCode)) {
			ps.deleteProject(admin, ps.getProjectByCode(pCode));			
		}
	}

	@Test
	public void createIteration() {
		assertNotNull("Iteration should not be null", iter);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createIterationWithNullProject() {
		is.createIteration(null, beginDate, endDate);
		fail("Exception not thrown when project was null");
	}

	@Test(expected = IllegalStateException.class)
	public void createIterationWithNonExistingProject() {
		ps.deleteProject(admin, project);
		is.createIteration(project, beginDate, endDate);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createIterationWithNullBeginDate() {
		is.createIteration(project, null, endDate);
		fail("Exception not thrown when Date was null");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createIterationWithNullEndDate() {
		is.createIteration(project, beginDate, null);
		fail("Exception not thrown when Date was null");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createIterationWitWrongDate() {
		is.createIteration(project, endDate.plusDays(5), endDate);
		fail("Exception not thrown when end date was before begin date");
	}	
	
	@Test(expected = IllegalStateException.class)
	public void deleteIteration() {	
		int iterId = iter.iterationId();
		is.deleteIteration(iter);
		is.getIterationById(iterId);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteNullIteration() {
		is.deleteIteration(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void deleteIterationThatDoesNotExist() {
		is.deleteIteration(iter);
		is.deleteIteration(iter);
	}
	
	@Test
	public void getIteration() {		
		int iterNumber = iter.number();
		iter = is.getIteration(project, iterNumber);
		assertNotNull("Iteration should not be null", iter);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getIterationWithNullProject() {
		is.getIteration(null, iter.number());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getIterationWithNegativeNumber() {
		is.getIteration(project, -5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getIterationByIdWithNegativeNumber() {
		is.getIterationById(-5);
	}
	
	@Test
	public void getIterationByIdWithTest() {
		Iteration iteration = is.getIterationById(iter.iterationId());
		assertNotNull("Iteration shouldn't be null", iteration);
	}
	
	@Test
	public void setBeginDate() {
		LocalDate newDate = iter.startDate().plusDays(1);
		iter = is.setBeginDate(iter, newDate);
		assertTrue(newDate.equals(iter.startDate()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setBeginDateWithBadDate() {
		is.setBeginDate(iter, iter.endDate().plusDays(1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setBeginWithNullDate() {
		is.setBeginDate(iter, null);
	}
	
	@Test
	public void setEndDate() {
		LocalDate newEndDate = iter.endDate().plusDays(1);
		iter = is.setEndDate(iter, newEndDate);
		assertTrue(newEndDate.equals(iter.endDate()));		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setEndDateWithNullDate() {
		is.setEndDate(iter, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setEndDateWithInvalidDate() {
		is.setEndDate(iter, beginDate.minusDays(5));
	}
	
	@Test
	public void getIterationsForProject() {
		assertNotNull("List should not be null", is.getIterationsForProject(project));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getIterationsForProjectWithNullProject() {
		is.getIterationsForProject(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getIterationsForProjectWithDeletedProject() {
		ps.deleteProject(admin, project);
		is.getIterationsForProject(project);
	}
	
	@Test
	public void theIntensiveTesterLightEdition(){
		int i;
		for (i = 0 ; i < 100; i++){
			iter = is.createIteration(project, LocalDate.now(), LocalDate.now().plusDays(15));
			is.deleteIteration(iter);
		}
	}
	
	@Test
	public void getParent() {
		Project pj = is.getParent(iter);
		assertEquals("Both projects should be equal, but they aren't", pj, project);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getParentWithNullProject(){
		is.getParent(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getParentWithInvalidProject(){
		is.deleteIteration(iter);
		is.getParent(iter);
	}
	
	@Test(expected = IllegalStateException.class)
	public void createOverlappingIteration() {
		is.createIteration(project, LocalDate.now(), LocalDate.now().plusDays(10));
		is.createIteration(project, LocalDate.now().plusDays(5), LocalDate.now().plusDays(15));
	}

	@Test(expected = IllegalStateException.class)
	public void getIterationByNumberFromInexistentProject() {
		is.getIteration(project, 50);
	}

	@Test(expected = IllegalStateException.class)
	public void getIterationByNumberForInexistentIteration() {
		is.deleteIteration(iter);
		is.getIteration(project, iter.number());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBeginDateToNullIteration() {
		is.setBeginDate(iter, null);
	}

	@Test(expected = IllegalStateException.class)
	public void setBeginDateToInexistentIteration() {
		is.deleteIteration(iter);
		is.setBeginDate(iter, LocalDate.now());
	}

	@Test(expected = IllegalStateException.class)
	public void setBeginDateToOverlapAnIteration() {
		is.createIteration(project, beginDate.minusDays(5), beginDate);
		is.setBeginDate(iter, beginDate.minusDays(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setEndDateToNullIteration() {
		is.setEndDate(iter, null);
	}

	@Test(expected = IllegalStateException.class)
	public void setEnDateToInexistentIteration() {
		is.deleteIteration(iter);
		is.setEndDate(iter, LocalDate.now());
	}

	@Test(expected = IllegalStateException.class)
	public void setEndDateToOverlapIteration() {
		is.createIteration(project, endDate, endDate.plusDays(5));
		is.setEndDate(iter, endDate.plusDays(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRangeBecauseStartDateIsNull() {
		is.isValidDateRangeInProject(project, null, endDate);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRangeBecauseEndDateIsNull() {
		is.isValidDateRangeInProject(project, beginDate, null);
	}

	@Test(expected = IllegalStateException.class)
	public void invalidDateRangeBecauseProjectDoesntExist() {
		ps.deleteProject(admin, project);
		is.isValidDateRangeInProject(project, beginDate.plusDays(20), endDate.plusDays(20));
	}

	@Test
	public void invalidDateRangeCauseCauseEndDateOverlaps() {
		is.createIteration(project, endDate.plusDays(1), endDate.plusDays(5));
		assertFalse(is.isValidDateRangeInProject(project, beginDate, endDate.plusDays(2)));
	}

	@Test
	public void invalidDateRangeCauseCauseStartDateOverlaps() {
		is.createIteration(project, beginDate.minusDays(5), beginDate.minusDays(1));
		assertFalse(is.isValidDateRangeInProject(project, beginDate.minusDays(2), endDate));
	}

	@Test
	public void invalidDateRangeCauseCauseDateContainsIteration() {
		is.createIteration(project, endDate, endDate.plusDays(5));
		assertFalse(is.isValidDateRangeInProject(project, beginDate, endDate.plusDays(10)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createIterationOnNullStartDateOnInherit() {
		is.createIteration(project, null, endDate, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createIterationOnNullEndDateOnInherit() {
		is.createIteration(project, beginDate, null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createIterationOnNullProjectOnInherit() {
		is.createIteration(null, beginDate, endDate, 1);
	}

	@Test(expected = IllegalStateException.class)
	public void createIterationOnInexistentProjectOnInherit() {
		ps.deleteProject(admin, project);
		is.createIteration(project, beginDate, endDate, 1);
	}

	@Test(expected = IllegalStateException.class)
	public void inheritTasksFromInexistentIteration() {
		is.deleteIteration(iter);
		is.createIteration(project, beginDate, endDate, 1);
	}

	@Test
	public void inheritTasksSuccesfully() {
		Story testStory = ss.create(iter, "Test story");
		ts.createTask(testStory, "Test task", null, Status.NOT_STARTED, admin, Score.NORMAL, Priority.NORMAL);
		Iteration inheritedIt = is.createIteration(project, beginDate.plusDays(20), endDate.plusDays(20), 1);
		Map<Story, List<Task>> inherTasks = ss.getStoriesWithTasksForIteration(inheritedIt);
		for (Story story: inherTasks.keySet()) {
			assertTrue(ts.taskNameExists(story, "Test task"));			
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLastFinishedIterationNumberFromNullProject() {
		is.getLastFinishedIterationNumber(null);
	}

	@Test(expected = IllegalStateException.class)
	public void getLastFinishedIterationNumberFromInexistentProject() {
		ps.deleteProject(admin, project);
		is.getLastFinishedIterationNumber(project);
	}

	@Test
	public void getLastFinishedIterationNumberForEmptyProject() {
		assertNull(is.getLastFinishedIterationNumber(project));
	}

	@Test
	public void getLastFinishedIterationNumberSuccesfully() {
		is.createIteration(project, beginDate.minusDays(50), endDate.minusDays(50));
		assertEquals(1, (int)is.getLastFinishedIterationNumber(project));
	}

}
