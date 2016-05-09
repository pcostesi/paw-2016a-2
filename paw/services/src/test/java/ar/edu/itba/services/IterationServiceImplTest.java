package ar.edu.itba.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public class IterationServiceImplTest {

	@Autowired
	IterationService is;

	@Autowired
	ProjectService ps;

	private Project project;
	private String projectName = "IterationTest";
	private LocalDate beginDate;
	private LocalDate endDate;
	private Iteration iter;

	@Before
	public void setup() {
		project = ps.createProject(projectName, "Project for testing Iteration service", "ITP");
		endDate = LocalDate.now();
		beginDate = endDate.minusDays(15);
		iter = is.createIteration(project, beginDate, endDate);
	}

	@Test
	public void createIteration() {
		iter = is.createIteration(project, beginDate, endDate);
		assertNotNull("Iteration should not be null", iter);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createIterationWithNullProject() {
		is.createIteration(null, beginDate, endDate);
		fail("Exception not thrown when project was null");
	}

	@Test(expected = IllegalStateException.class)
	public void createIterationWithNonExistingProject() {
		Project errasable = ps.createProject("Turu", "Tu", "tu");
		ps.deleteProject(errasable);
		is.createIteration(errasable, beginDate, endDate);
		fail("Exception not thrown when project didn't exist");
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
		Iteration newIter = is.createIteration(project, beginDate.plusDays(20), endDate.plusDays(20));
		int iterId = newIter.iterationId();
		is.deleteIteration(newIter);
		is.getIterationById(iterId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNullIteration() {
		is.deleteIteration(null);
	}

	@Test(expected = IllegalStateException.class)
	public void deleteIterationThatDoesNotExist() {
		Iteration newIter = is.createIteration(project, beginDate.plusDays(20), endDate.plusDays(20));
		is.deleteIteration(newIter);
		is.deleteIteration(newIter);
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
		is.setBeginDate(iter, newDate);
		assertTrue(newDate.equals(iter.startDate()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBeginDateWithBadDate() {
		is.setBeginDate(iter, iter.endDate().plusDays(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBeginWithNullDate() {
		is.createIteration(project, beginDate, endDate);
		is.setBeginDate(iter, null);
	}

	@Test
	public void setEndDate() {
		LocalDate newEndDate = iter.endDate().plusDays(1);
		is.setEndDate(iter, newEndDate);
		assertTrue(newEndDate.equals(iter.endDate()));
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void setEndDateWithNullDate() {
		Iteration iter = is.createIteration(project, beginDate, endDate);
		is.setEndDate(iter, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setEndDateWithInvalidDate() {
		Iteration iter = is.createIteration(project, beginDate, endDate);
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
		Project newProject = ps.createProject("NewTesting", "Insert Description", "jgf");
		ps.deleteProject(newProject);
		is.getIterationsForProject(newProject);
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
	
	@Test(expected = IllegalStatetException.class)
	public void getParentWithInvalidProject(){
		Iteration newIter = is.createIteration(project, beginDate.plusDays(40), endDate.plusDays(40));
		is.deleteIteration(newIter);
		is.getParent(newIter);
	}
}
