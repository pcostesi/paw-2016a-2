package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {

    private static final String name = "Tester";
    private static final String mail = "tester@tester.com";
    private static final String password = "password";
    private static final String unusedName = "testSafeName";
    private static final String unusedMail = "test@tester.com.ar";
    private static final String empty = "";
    private static final String longString = "thisisalongstringthisisalongstringthisisalongstringthisisalongstringthisisalongstring"
            + "thisisalongstringthisisalongstringthisisalongstringthisisalongstringthisisalongstringthisisalongstringthisisalongstring";
    
    private static final String memberName = "Member";
    private static final String memberMail = "Member@tester.com";
    private static final String memberPassword = "password";
    
    private static final String projectName = "Project name";
    private static final String projectDescr = "Project descr";
    private static final String projectCode = "CODE9123";
    
    private User user, member;
    private Project project;

    @Autowired
    private UserService us;

    @Autowired
    private ProjectService ps;

    @Before
    public void setup() {
        user = us.create(name, password, mail);
        member = us.create(memberName, memberPassword, memberMail);
        project = ps.createProject(user, new HashSet<>(), projectName, projectDescr, projectCode);
    }
    
    @After
    public void tearDown() {
    	if (ps.projectCodeExists(projectCode)) {
    		ps.deleteProject(user, project);
    	}
    	us.deleteUser(user);
    	us.deleteUser(member);
    }

    @Test
    public void createUserTest() {
        assertNotNull(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullName() {
        us.create(null, password, mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatWithNullPassword() {
        us.create(name, null, mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullMail() {
        us.create(name, password, null);
    }

    @Test(expected = IllegalStateException.class)
    public void createWithUsedName() {
        us.create(name, password, unusedMail);
    }

    @Test(expected = IllegalStateException.class)
    public void createWithUsedMail() {
        us.create(unusedName, password, mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyPassword() {
        us.create(unusedName, empty, unusedMail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyName() {
        us.create(empty, password, unusedMail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyMail() {
        us.create(unusedName, password, empty);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithLongName() {
        us.create(longString, password, unusedMail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithLongPassword() {
        us.create(unusedName, longString, unusedMail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithLongMail() {
        us.create(unusedName, password, longString + unusedMail);
    }

    @Test
    public void getByUsernameTest() {
        assertTrue(us.getByUsername(user.username()).equals(user));
    }

    @Test(expected = IllegalStateException.class)
    public void getByinvalidUserName() {
        us.getByUsername("Fake it!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByusernameWithNull() {
        us.getByUsername(null);
    }

    @Test
    public void getUsernamesTest() {
        int i;
        int usersBefore = us.getUsernames().size();
        for (i = 100; i < 1100; i++) {
            us.create(String.valueOf(i), password, String.valueOf(i) + "@" + String.valueOf(i) + ".com");
        }
        assertTrue(usersBefore + 1000 == us.getUsernames().size()); //+1 due to @Before
    }

    @Test
    public void usernameExistsTest() {
        assertTrue(us.usernameExists(user.username()));
        assertFalse(us.usernameExists("fake"));
    }

    @Test
    public void usernameExistsWithNull() {
        assertTrue(us.emailExists(user.mail()));
        assertFalse(us.emailExists("fake"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailExistsTest() {
        us.emailExists(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailExistsWithNull() {
        us.usernameExists(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableUsersForNullProject() {
        us.getAvailableUsers(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getAvailableUsersForInexistentProject() {
    	ps.deleteProject(user, project);
        us.getAvailableUsers(project);
    }

    @Test
    public void getAvailableUsersSuccesfully() {
        final User testUser = us.create("Testerino69", "passwordio", "mail@abs2.com");
        assertTrue(us.getAvailableUsers(project).contains(testUser.username()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUsernamesForNullProject() {
        us.getUsernamesForProject(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getUsernamesForInexistentProject() {
        ps.deleteProject(user, project);
        us.getUsernamesForProject(project);
    }

    @Test
    public void getUserNamesForProjectSuccesfully() {
    	ps.addUserToProject(user, project, member);
        assertTrue(us.getUsernamesForProject(project).contains(member.username()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUsernamesExceptNullUser() {
        us.getUsernamesExcept(null);
    }

    @Test
    public void getUsernamesExceptMyselfSuccesfully() {
        User testUser = us.create("Testerino2", "passwordio", "mail@a.com");
        assertTrue(us.getUsernamesExcept(user).contains(testUser.username()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void editPasswordForNullUser() {
        us.editPassword(null, "newPassword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void editPasswordToNull() {
        us.editPassword(user, null);
    }

    @Test
    public void editPasswordSuccesfully() {
        String newPassword = "EuCuliaoh";
        us.editPassword(user, newPassword);
        assertEquals(newPassword, us.getByUsername(name).password());
    }

}