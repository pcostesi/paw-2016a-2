package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.BacklogDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.UserDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
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

import javax.persistence.PersistenceException;
import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BacklogDaoTest {

    private final String pName = "TesterProject";
    private final String pCode = "Test";
    private final String title = "A pretty Title";
    private final String description = "A lovley description";
    private final String newTitle = "This replaces a pretty title";
    private final String newDescription = "This replaces a loveley description";
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DataSource ds;
    @Autowired
    private BacklogDao bd;
    @Autowired
    private UserDao userDao;
    private User owner;
    private Project project;
    private JdbcTemplate jdbcTemplate;
    private BacklogItem item;

    @Before
    public void setup() {
        if (userDao.userNameExists("testuser")) {
            owner = userDao.getByUsername("testuser");
        } else {
            owner = userDao.createUser("testuser", "test", "testerr@gmail.com");
        }
        jdbcTemplate = new JdbcTemplate(ds);
        project = projectDao.createProject(owner, pName, "Best Project EVAR", pCode);
    }

    @After
    public void after() {
        projectDao.deleteProject(project);
    }

    @Test
    public void createTest() {
        item = bd.createBacklogItem(title, description, project);
        assertNotNull(item);
        assertEquals(item.description().get(), description);
        assertEquals(item.title(), title);
        assertEquals(item.project(), project);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 1);
    }

    @Test
    public void getTest() {
        item = bd.createBacklogItem(title, description, project);
        item = bd.getBacklogItemById(item.backlogItemId());
        assertNotNull(item);
        assertEquals(item.description().get(), description);
        assertEquals(item.title(), title);
        assertEquals(item.project(), project);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 1);
    }

    @Test(expected = PersistenceException.class)
    public void createCreatedTest() {
        item = bd.createBacklogItem(title, description, project);
        item = bd.createBacklogItem(title, description, project);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 0);
    }

    @Test(expected = PersistenceException.class)
    public void createNullProjectTest() {
        item = bd.createBacklogItem(title, description, null);
    }

    @Test
    public void deleteItem() {
        item = bd.createBacklogItem(title, description, project);
        bd.deleteItem(item);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 0);
    }

    public void deleteDelete() {
        item = bd.createBacklogItem(title, description, project);
        bd.deleteItem(item);
        bd.deleteItem(item);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 0);
    }

    @Test
    public void backlogItemExistsWithExistent() {
        item = bd.createBacklogItem(title, description, project);
        assertTrue(bd.backlogItemExists(item));
    }

    @Test
    public void backlogItemExistsWithInexistent() {
        item = bd.createBacklogItem(title, description, project);
        bd.deleteItem(item);
        assertFalse(bd.backlogItemExists(item));
    }

    @Test
    public void backlogItemExistsByProject() {
        item = bd.createBacklogItem(title, description, project);
        assertTrue(bd.backlogItemExists(project, title));
    }

    @Test
    public void backlogItemInexistentByProject() {
        item = bd.createBacklogItem(title, description, project);
        bd.deleteItem(item);
        assertFalse(bd.backlogItemExists(project, title));
    }

    @Test
    public void updateTitle() {
        item = bd.createBacklogItem(title, description, project);
        bd.updateTitle(item, newTitle);
        item = bd.getBacklogItemById(item.backlogItemId());
        assertEquals(item.title(), newTitle);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 1);
    }

    @Test
    public void updateDescription() {
        item = bd.createBacklogItem(title, description, project);
        bd.updateDescription(item, newDescription);
        item = bd.getBacklogItemById(item.backlogItemId());
        assertEquals(item.description().get(), newDescription);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 1);
    }

    @Test
    public void getBacklogForProject() {
        int i;
        for (i = 0; i < 150; i++) {
            item = bd.createBacklogItem(title + String.valueOf(i), description + String.valueOf(i), project);
        }
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "backlog") == 150);
        assertEquals(bd.getBacklogForProject(project).size(), 150);
    }

    @Test
    public void getParent() {
        item = bd.createBacklogItem(title, description, project);
        assertEquals(bd.getParent(item), project);
    }

    @Test
    public void getBacklogItemById() {
        item = bd.createBacklogItem(title, description, project);
        assertEquals(bd.getBacklogItemById(item.backlogItemId()), item);
    }
}
