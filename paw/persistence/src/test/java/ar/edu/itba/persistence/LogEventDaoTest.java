package ar.edu.itba.persistence;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

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

import ar.edu.itba.interfaces.dao.LogEventDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.UserDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.ProjectCreatedEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LogEventDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private LogEventDao logEventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private Project testProject;
    private User owner;

    private final String pName = "TesterProject";
    private final String pCode = "Test";

    @Before
    public void setUp() throws Exception {
        if (userDao.userNameExists("testuser")) {
            owner = userDao.getByUsername("testuser");
        } else {
            owner = userDao.createUser("testuser", "test", "testerr@gmail.com");
        }
        jdbcTemplate = new JdbcTemplate(ds);
        testProject = projectDao.createProject(owner, pName, "Best Project EVAR", pCode);
    }

    @After
    public void after() {
        projectDao.deleteProject(testProject);
    }

    @Test
    public void persistTaskWithCorrectParametersTest() {
        final ProjectCreatedEvent event = new ProjectCreatedEvent();
        event.setActor(owner);
        event.setProject(testProject);
        event.setTime(LocalDateTime.now());

        logEventDao.insertEvent(event);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "event") == 1);
    }
}
