package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

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
    private LocalDate startDate;
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
        startDate = endDate.minusDays(15);
        iter = is.createIteration(project, startDate, endDate);
    }

    @After
    public void endingSetup() {
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
        is.createIteration(null, startDate, endDate);
        fail("Exception not thrown when project was null");
    }

    @Test(expected = IllegalStateException.class)
    public void createIterationWithNonExistingProject() {
        ps.deleteProject(admin, project);
        is.createIteration(project, startDate, endDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIterationWithNullBeginDate() {
        is.createIteration(project, null, endDate);
        fail("Exception not thrown when Date was null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIterationWithNullEndDate() {
        is.createIteration(project, startDate, null);
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
    public void theIntensiveTesterLightEdition() {
        int i;
        for (i = 0; i < 100; i++) {
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
    public void getParentWithNullProject() {
        is.getParent(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getParentWithInvalidProject() {
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
    public void createIterationOnNullStartDateOnInherit() {
        is.createIteration(project, null, endDate, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIterationOnNullEndDateOnInherit() {
        is.createIteration(project, startDate, null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIterationOnNullProjectOnInherit() {
        is.createIteration(null, startDate, endDate, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void createIterationOnInexistentProjectOnInherit() {
        ps.deleteProject(admin, project);
        is.createIteration(project, startDate, endDate, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void inheritTasksFromInexistentIteration() {
        is.deleteIteration(iter);
        is.createIteration(project, startDate, endDate, 1);
    }

    @Test
    public void inheritTasksSuccesfully() {
        Story testStory = ss.create(iter, "Test story");
        ts.createTask(testStory, "Test task", null, Status.NOT_STARTED, admin, Score.NORMAL, Priority.NORMAL);
        Iteration inheritedIt = is.createIteration(project, startDate.plusDays(20), endDate.plusDays(20), 1);
        Map<Story, List<Task>> inherTasks = ss.getStoriesWithTasksForIteration(inheritedIt);
        for (Story story : inherTasks.keySet()) {
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
        is.createIteration(project, startDate.minusDays(50), endDate.minusDays(50));
        assertEquals(1, (int) is.getLastFinishedIterationNumber(project));
    }

}
