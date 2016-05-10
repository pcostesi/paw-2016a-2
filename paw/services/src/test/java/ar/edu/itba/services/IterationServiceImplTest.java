package ar.edu.itba.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class IterationServiceImplTest {

	@Autowired
	IterationService is;

	@Autowired
	ProjectService ps;

	private Project project;
	private String pName = "IterationTest";
	private String pDesc = "Project for testing Iteration service";
	private String pCode = "ITP";
	private LocalDate beginDate;
	private LocalDate endDate;
	private Iteration iter;

	@Before
	public void setup() {
		project = ps.createProject(pName, pDesc, pCode);
		endDate = LocalDate.now();
		beginDate = endDate.minusDays(15);
		iter = is.createIteration(project, beginDate, endDate);
	}
	
	@After
	public void endingSetup(){
		if(ps.projectCodeExists(pCode)){
			List<Iteration> list = is.getIterationsForProject(project);
			if(!list.isEmpty()){
				
				list.forEach(iteration -> is.deleteIteration(iteration));
			}
			ps.deleteProject(ps.getProjectByCode(pCode));
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
		ps.deleteProject(project);
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
	/*Destroys DB, intensive tester WINS!
	@Test
	public void theIntensiveTester(){
		int i;
		for (i = 0 ; i < 100; i++){
			is.createIteration(project, LocalDate.now(), LocalDate.now().plusDays(15));
		}
		List<Iteration> list = is.getIterationsForProject(project);
		System.out.println(list.size());
		if(!list.isEmpty()){		
			list.forEach((iteration) -> {System.out.println(iteration.iterationId()); is.deleteIteration(iteration); System.out.println("Borre 1");});
		}
		System.out.println("Borre 100");
	}*/
	
	@Test(expected = IllegalArgumentException.class)
	public void getIterationsForProjectWithNullProject() {
		is.getIterationsForProject(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getIterationsForProjectWithDeletedProject() {
		ps.deleteProject(project);
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
	
}
