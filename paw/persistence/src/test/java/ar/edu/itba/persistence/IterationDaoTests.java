package ar.edu.itba.persistence;

import static org.junit.Assert.*;

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
import ar.edu.itba.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class IterationDaoTests {

	@Autowired
	private IterationDao id;

	@Autowired
	private ProjectDao pd;

	@Autowired
	private UserDao ud;

	@Autowired
	private DataSource ds;

	private final String pName = "TesterProject";
	private final String pCode = "Test";
	private final String tName = "Test Task";
	private final String tDesc = "The tester's life is a tough one";
	private final Status status = Status.NOT_STARTED;
	private final Score score = Score.EASY;
	private final Priority priority = Priority.NORMAL;

	private JdbcTemplate jdbcTemplate;
	private Iteration iteration;
	private Project project;
	private User owner;
	private LocalDate startDate;
	private LocalDate endDate;
	private int number;

	@Before
	public void setUp() {
		if (ud.userNameExists("testuser")) {
			owner = ud.getByUsername("testuser");
		} else {
			owner = ud.createUser("testuser", "test", "testerr@gmail.com");
		}
		jdbcTemplate = new JdbcTemplate(ds);
		project = pd.createProject(owner, pName, "Best Project EVAR", pCode);
		startDate = LocalDate.now();
		endDate = LocalDate.now().plusDays(15);
		number = 1;
	}

	@After
	public void after() {
		pd.deleteProject(project);
	}

	@Test
	public void testing() {
		iteration = id.createIteration(project, number, startDate, endDate);
		assertNotNull(iteration);
		assertEquals(iteration.project(), project);
		assertEquals(iteration.number(), number);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "iteration") == 1);
	}

	@Test
	public void deleteIteration() {
		iteration = id.createIteration(project, number, startDate, endDate);
		id.deleteIteration(iteration);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "iteration") == 0);
	}

	@Test
	public void getIteration() {
		iteration = id.createIteration(project, number, startDate, endDate);
		iteration = id.getIteration(project, number);
		assertNotNull(iteration);
		assertEquals(iteration.project(), project);
		assertEquals(iteration.number(), number);
	}

	@Test
	public void getIterationById() {
		iteration = id.createIteration(project, number, startDate, endDate);
		iteration = id.getIterationById(iteration.iterationId());
		assertNotNull(iteration);
		assertEquals(iteration.project(), project);
		assertEquals(iteration.number(), number);
	}

	@Test
	public void iterationExists() {
		iteration = id.createIteration(project, number, startDate, endDate);
		assertTrue(id.iterationExists(iteration));
	}

	@Test
	public void inexistentIterationExists() {
		iteration = id.createIteration(project, number, startDate, endDate);
		id.deleteIteration(iteration);
		assertFalse(id.iterationExists(iteration));
	}

	@Test
	public void updateStartDate() {
		iteration = id.createIteration(project, number, startDate, endDate);
		id.updateStartDate(iteration, startDate.plusDays(5));
		iteration = id.getIterationById(iteration.iterationId());
		assertEquals(iteration.startDate(), startDate.plusDays(5));
	}

	@Test
	public void updateEndDate() {
		iteration = id.createIteration(project, number, startDate, endDate);
		id.updateEndDate(iteration, endDate.plusDays(5));
		iteration = id.getIterationById(iteration.iterationId());
		assertEquals(iteration.endDate(), endDate.plusDays(5));
	}

	@Test
	public void getIterationsForProject() {
		int i;
		for (i = 0; i < 150; i++) {
			iteration = id.createIteration(project, number + i, startDate.plusDays(i + 1), endDate.plusDays(i + 1));
		}
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "iteration") == 150);
		assertEquals(id.getIterationsForProject(project).size(), 150);
	}

	@Test
	public void getParents() {
		iteration = id.createIteration(project, number, startDate, endDate);
		assertEquals(id.getParent(iteration), project);
	}

	@Test
	public void increaseNumberOfIterationNumbered() {
		iteration = id.createIteration(project, number, startDate, endDate);
		id.increaseNumberOfIterationNumbered(project, number);
		iteration = id.getIterationById(iteration.iterationId());
		assertEquals(iteration.number(), number + 1);
	}

	@Test
	public void decreaseNumberOfIterationNumbered() {
		iteration = id.createIteration(project, number + 1, startDate, endDate);
		id.decreaseNumberOfIterationNumbered(project, number + 1);
		iteration = id.getIterationById(iteration.iterationId());
		assertEquals(iteration.number(), number);
	}

	@Test
	public void getMaxNumber() {
		int i;
		for (i = 0; i < 51; i++) {
			iteration = id.createIteration(project, number + i, startDate.plusDays(i + 1), endDate.plusDays(i + 1));
		}
		assertEquals(id.getMaxNumber(project), number + i - 1);
	}

}
