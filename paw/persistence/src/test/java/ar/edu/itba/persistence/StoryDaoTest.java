package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.IterationDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.UserDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoryDaoTest {

    private final String pName = "TesterProject";
    private final String pCode = "Test";
    private final String title = "This story has a wonderfull title";
    @Autowired
    private StoryDao sd;
    @Autowired
    private IterationDao id;
    @Autowired
    private ProjectDao pd;
    @Autowired
    private UserDao ud;
    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;
    private Story story;
    private Iteration iteration;
    private Project project;
    private User owner;
    private int number;

    @Before
    public void setUp() throws Exception {
        if (ud.userNameExists("testuser")) {
            owner = ud.getByUsername("testuser");
        } else {
            owner = ud.createUser("testuser", "test", "testerr@gmail.com");
        }
        jdbcTemplate = new JdbcTemplate(ds);
        project = pd.createProject(owner, pName, "Best Project EVAR", pCode);
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now().plusDays(15);
        iteration = id.createIteration(project, number, startDate, endDate);

    }

    @After
    public void after() {
        pd.deleteProject(project);
    }

    @Test
    public void createStory() {
        story = sd.createStory(iteration, title);
        assertNotNull(story);
        assertEquals(story.title(), title);
        assertEquals(story.iteration(), iteration);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "story") == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void createCreated() {
        story = sd.createStory(iteration, title);
        story = sd.createStory(iteration, title);
    }

    @Test
    public void delete() {
        story = sd.createStory(iteration, title);
        sd.deleteStory(story);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "story") == 0);
    }

    @Test
    public void deleteDeleted() {
        story = sd.createStory(iteration, title);
        sd.deleteStory(story);
        sd.deleteStory(story);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "story") == 0);
    }

    @Test
    public void storyExists() {
        story = sd.createStory(iteration, title);
        assertTrue(sd.storyExists(story));
    }

    @Test
    public void inexistentStoryExists() {
        story = sd.createStory(iteration, title);
        sd.deleteStory(story);
        assertFalse(sd.storyExists(story));
    }

    @Test
    public void udpateTitle() {
        final String newTitle = "random";
        story = sd.createStory(iteration, title);
        sd.updateTitle(story, newTitle);
        story = sd.getStoryById(story.storyId());
        assertEquals(story.title(), newTitle);
    }

    @Test
    public void getStoryById() {
        story = sd.createStory(iteration, title);
        story = sd.getStoryById(story.storyId());
        assertEquals(story.iteration(), iteration);
        assertEquals(story.title(), title);
    }

    @Test(expected = IllegalStateException.class)
    public void getInexistentStoryById() {
        story = sd.createStory(iteration, title);
        sd.deleteStory(story);
        sd.getStoryById(story.storyId());
    }

    @Test
    public void getParent() {
        story = sd.createStory(iteration, title);
        assertEquals(sd.getParent(story), iteration);
    }

    @Test
    public void getStoriesForIteration() {
        int i;
        for (i = 0; i < 50; i++) {
            sd.createStory(iteration, "Lovley Generic Title " + i);
        }
        assertEquals(sd.getStoriesForIteration(iteration).size(), 50);
    }
}
