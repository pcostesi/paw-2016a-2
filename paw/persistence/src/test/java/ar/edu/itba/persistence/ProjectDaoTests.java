package ar.edu.itba.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.UserDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProjectDaoTests {

	@Autowired
	private UserDao ud;
	
	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DataSource ds;
	
	private User user;
	private JdbcTemplate jdbcTemplate;
	private String name = "Braulius";
	private String password = "secret password";
	private String mail = "braulius@braulicus.com";
	private String projectName = "projName";
	private String projectCode= "projCode";
	private String projectDescrp= "this is a description";
	
	@Before
	public void setup(){
		jdbcTemplate = new JdbcTemplate(ds);
		if (ud.userNameExists(name)) {
			user = ud.getByUsername(name);
		} else {
			user = ud.createUser(name, password, mail);			
		}		
	}
	
	@After
	public void after() {
		if (projectDao.projectNameExists(projectName)) {
			projectDao.deleteProject(projectDao.getProjectByCode(projectCode));
		}
	}
	
	@Test
	public void checkProjectExist() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		assertTrue(projectDao.projectExists(proj));		
	}
	
	@Test
	public void checkProjectDoesntExist() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.deleteProject(proj);
		assertFalse(projectDao.projectExists(proj));		
	}
	
	@Test
	public void checkProjectNameExists() {
		projectDao.createProject(user, projectName, projectDescrp, projectCode);
		assertTrue(projectDao.projectNameExists(projectName));
	}
	
	@Test
	public void checkProjectNameDoesntExists() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.deleteProject(proj);
		assertFalse(projectDao.projectNameExists(projectName));
	}
	
	@Test
	public void checkProjectCreates() {
		projectDao.createProject(user, projectName, projectDescrp, projectCode);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "project") == 1);		
	}
	
	@Test
	public void checkProjectDelete() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.deleteProject(proj);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "project") == 0);
	}
	
	@Test
	public void removeProjectFromProjectUser() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.addProjectMember(proj, user);
		projectDao.deleteProjectUser(proj);
		assertTrue(JdbcTestUtils.countRowsInTable(jdbcTemplate, "project_user") == 0);
	}
	
	@Test
	public void updateName() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.updateName(proj, "newName");
		assertTrue(projectDao.projectNameExists("newName"));
	}
	
	@Test
	public void updateDescrp() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.updateDescription(proj, "newDescrp");
		assertTrue(projectDao.getProjectById(proj.projectId()).description().equals("newDescrp"));
	}
	
	@Test
	public void updateCode() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.updateCode(proj, "newCode");
		assertTrue(projectDao.getProjectById(proj.projectId()).code().equals("newCode"));
	}
	
	@Test
	public void getProjectForUser() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.addProjectMember(proj, user);
		assertTrue(projectDao.getProjectsForUser(user).contains(proj));
	}
	
	@Test
	public void getProjectByCode() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		assertTrue(projectDao.getProjectByCode(projectCode).equals(proj));
	}
	
	@Test
	public void getProjectById() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		assertTrue(projectDao.getProjectById(proj.projectId()).equals(proj));
	}
	
	@Test
	public void testUserWasAdded() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.addProjectMember(proj, user);
		assertTrue(projectDao.getProjectMembers(proj).contains(user));
	}
	
	@Test
	public void testUserWasDeleted() {
		Project proj = projectDao.createProject(user, projectName, projectDescrp, projectCode);
		projectDao.addProjectMember(proj, user);
		projectDao.deleteProjectMember(proj, user);
		assertFalse(projectDao.getProjectMembers(proj).contains(user));
	}
}
