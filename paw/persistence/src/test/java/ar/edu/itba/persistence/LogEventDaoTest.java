package ar.edu.itba.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

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
import ar.edu.itba.models.event.LogEvent;
import ar.edu.itba.models.event.ProjectCreatedEvent;
import ar.edu.itba.models.event.UserCreatedEvent;

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

    private final String pName = "ProjectLogEvent";
    private final String pCode = "PLEvent";

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(ds);

        if (userDao.userNameExists("testuser")) {
            owner = userDao.getByUsername("testuser");
        } else {
            owner = userDao.createUser("testuser", "test", "testerr@gmail.com");
        }

        if (projectDao.projectCodeExists(pCode)) {
            testProject = projectDao.getProjectByCode(pCode);
        } else {
            testProject = projectDao.createProject(owner, pName, "Best Project EVAR", pCode);
        }
    }

    @Test
    public void persistTaskWithCorrectParametersTest() {
        final int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "event");

        final ProjectCreatedEvent event = new ProjectCreatedEvent();
        event.setActor(owner);
        event.setProject(testProject);
        event.setTime(LocalDateTime.now());

        logEventDao.insertEvent(event);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "event") == rows + 1);
    }

    @Test
    public void persistTwoDifferentTasksWithCorrectParametersTest() {
        final int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "event");
        final ProjectCreatedEvent event = new ProjectCreatedEvent();
        event.setActor(owner);
        event.setProject(testProject);
        event.setTime(LocalDateTime.now());

        final UserCreatedEvent event2 = new UserCreatedEvent();
        event2.setActor(owner);
        event2.setProject(testProject);
        event2.setTime(LocalDateTime.now());
        event2.setCreated(owner);

        logEventDao.insertEvent(event);
        logEventDao.insertEvent(event2);
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "event") == rows + 2);
    }

    @Test
    public void getEventsForActorTest() {
        final int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "event");
        final ProjectCreatedEvent event = new ProjectCreatedEvent();
        event.setActor(owner);
        event.setProject(testProject);
        event.setTime(LocalDateTime.now());

        final UserCreatedEvent event2 = new UserCreatedEvent();
        event2.setActor(owner);
        event2.setProject(testProject);
        event2.setTime(LocalDateTime.now());
        event2.setCreated(owner);

        logEventDao.insertEvent(event);
        logEventDao.insertEvent(event2);

        final List<? extends LogEvent> events = logEventDao.getEventsForActor(owner);
        assertTrue(events.contains(event));
        assertTrue(events.contains(event2));
        assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "event") == rows + 2);
    }

}
