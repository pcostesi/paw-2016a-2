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
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProjectServiceImplTest {

    private final String pName = "Test Project";
    private final String pDesc = "This project was created for testing purpouses";
    private final String pCode = "tp";
    private final String adminName = "adminName";
    private final String auxUserName = "auxUserName";
    @Autowired
    private ProjectService ps;
    @Autowired
    private UserService us;
    private User admin;
    private User auxUser;
    private Set<User> members;

    private Project testProject;

    @Before
    public void setup() {
        if (us.usernameExists(adminName)) {
            admin = us.getByUsername(adminName);
        } else {
            admin = us.create(adminName, "testPass", "testAdmin@mail.com");
        }
        if (us.usernameExists(auxUserName)) {
            auxUser = us.getByUsername(auxUserName);
        } else {
            auxUser = us.create(auxUserName, "testPass", "testAux@mail.com");
        }
        members = new HashSet<User>();
        testProject = ps.createProject(admin, members, pName, pDesc, pCode);
    }

    @After
    public void endingSetup() {
        if (ps.projectCodeExists(pCode)) {
            ps.deleteProject(admin, ps.getProjectByCode(pCode));
        }
    }

    @Test
    public void createProjectTest() {
        assertNotNull("Project should not be null", testProject);
    }

    @Test
    public void getProjectTest() {
        assertNotNull("Project should not be null", ps.getProjectByCode(pCode));
    }

    @Test(expected = IllegalStateException.class)
    public void deleteProjectTest() {
        ps.deleteProject(admin, testProject);
        ps.getProjectByCode(pCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullNameProject() {
        ps.createProject(admin, members, null, pDesc, pCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullDescProject() {
        ps.createProject(admin, members, pName, null, pCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullCodeProject() {
        ps.createProject(admin, members, pName, pDesc, null);
    }

    @Test(expected = IllegalStateException.class)
    public void createExistingProject() {
        ps.createProject(admin, members, pName, pDesc, pCode);
        ps.createProject(admin, members, pName, pDesc, pCode);
        ps.createProject(admin, members, pName, pDesc, pCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getProjectWithNull() {
        ps.getProjectByCode(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getInexistingProject() {
        ps.getProjectByCode("fakecode");
    }

    @Test(expected = IllegalStateException.class)
    public void doubleDeleteTest() {
        ps.deleteProject(admin, ps.getProjectByCode(pCode));
        ps.deleteProject(admin, ps.getProjectByCode(pCode));
    }

    @Test
    public void setNewName() {
        String newName = "Super Name";
        testProject = ps.setName(admin, testProject, newName);
        assertSame("should be same", testProject.name(), newName);
    }

    @Test
    public void setNewDescrpition() {
        testProject = ps.setDescription(admin, testProject, "This is the projects new description");
        assertSame("should be same", testProject.description(), "This is the projects new description");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidName() {
        ps.setName(admin, testProject, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidLongName() {
        ps.setName(admin, testProject, "asdasdasj kjsabfksbaf kj dbs fkljdsbfkl jsdb fkjsdbflksdjbfiewif bB FKDB KfbIEBUfbeeufib DifbsIFBo bbDuf yufs busdybf sidyfbysudfb xcv jbesui");
    }

    @Test
    public void theIntensiveTester() {
        int i;
        Project subject;
        for (i = 0; i < 300; i++) {
            subject = ps.createProject(admin, members, "Inte", "nsive", "tester");
            ps.deleteProject(admin, subject);
        }
        subject = ps.createProject(admin, members, "Inte", "nsive", "tester");
        assertNotNull("It should be ok", subject);
    }

    @Test
    public void theIntensiveTesterSDeluxeEdition() {
        int i;
        String string;
        for (i = 0; i < 700; i++) {
            string = String.valueOf(i);
            ps.createProject(admin, members, string, string, string);
        }
        for (i = 0; i < 700; i++) {
            string = String.valueOf(i);
            ps.deleteProject(admin, ps.getProjectByCode(string));
        }
    }

    @Test
    public void setCodeTest() {
        Project testMonkey;
        try {
            ps.setCode(admin, testProject, null);
            fail("Exception not thrown when code was null");
        } catch (Exception e) {
        }

        try {
            ps.setCode(admin, testProject, "");
            fail("Exception not thrown when code was empty");
        } catch (Exception e) {
        }

        try {
            ps.setCode(admin, testProject, "djsdjfsldkfjsldkfjslkdfjsdlkfjskldfjsldkf");
            fail("Exception not thrown when code was to long");
        } catch (Exception e) {
        }

        try {
            ps.setCode(admin, testProject, "ASJKDbaS Kdhsa bfasjdh");
            fail("Exception not thrown when code was invalid");
        } catch (Exception e) {
        }

        try {
            ps.setCode(admin, null, "tcc");
            fail("Exception not thrown when project was null");
        } catch (Exception e) {
        }

        try {
            testMonkey = ps.createProject(admin, members, pName + "a", pDesc + "a", pCode + "a");
            ps.deleteProject(admin, testMonkey);
            ps.setCode(admin, testMonkey, "tcc");
            fail("Exception not thrown when project was inexistent");
        } catch (Exception e) {
        }

        testMonkey = ps.createProject(admin, members, "setCodeTester", "Idem", "sc");
        testMonkey = ps.setCode(admin, testMonkey, "nc");
        assertSame("Code should be nc, but it was not", testMonkey.code(), "nc");
        ps.deleteProject(admin, testMonkey);
    }

    @Test
    public void getProjectByIdTest() {
        testProject = ps.createProject(admin, members, "getProjectTester", "Shalalalal", "gpt");
        assertNotNull("project should not be null", ps.getProjectById(testProject.projectId()));

        try {
            ps.getProjectById(-1);
            fail("Exception not thrown when ID was negative");
        } catch (Exception e) {
        }
        try {
            ps.getProjectById(645654);
            fail("Exception not thrown when ID was not found");
        } catch (Exception e) {
        }
    }

    @Test
    public void getProjectByCodeTest() {
        try {
            ps.getProjectByCode(null);
            fail("Exception not thrown when code was null");
        } catch (Exception e) {
        }
        try {
            ps.getProjectByCode("");
            fail("Exception not thrown when code was an empty String");
        } catch (Exception e) {
        }
        try {
            ps.getProjectByCode("ASUODH78y8752bhbjbgxc 9t9sd8gf g843");
            fail("Exception not thrown when code was not valid");
        } catch (Exception e) {
        }
        try {
            ps.getProjectByCode("nosuchcode");
            fail("Exception not thrown when code was inexistent in db");
        } catch (Exception e) {
        }
        assertNotNull("project should not be null", ps.getProjectByCode(pCode));
    }

    @Test
    public void getProjectsForExistingUser() {
        assertTrue(ps.getProjectsForUser(admin).contains(testProject));
    }

    @Test(expected = IllegalArgumentException.class)
    public void projectNameExistsIsNull() {
        ps.projectNameExists(null);
    }

    @Test
    public void projectNameExistsActuallyExists() {
        assertTrue(ps.projectNameExists(pName));
    }

    @Test
    public void projectNameExistsDoesntExists() {
        assertFalse(ps.projectNameExists("No existo"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void getProjectMembersForNullProject() {
        ps.getProjectMembers(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getProjectMembersForInexistentProject() {
        ps.deleteProject(admin, testProject);
        ps.getProjectMembers(testProject);
    }

    @Test
    public void getProjectMembersForExistentProject() {
        assertEquals(ps.getProjectMembers(testProject).get(0), admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void makeNullAdminAddUser() {
        ps.addUserToProject(null, testProject, auxUser);
    }

    @Test(expected = IllegalStateException.class)
    public void makeAdminAddUserToInexistentProject() {
        ps.deleteProject(admin, testProject);
        ps.addUserToProject(admin, testProject, auxUser);
    }

    @Test(expected = IllegalStateException.class)
    public void makeAdminAddUserToOtherProject() {
        Project testProject2 = ps.createProject(auxUser, new HashSet<User>(), "NewMemberProject", "description", "codeTest");
        ps.addUserToProject(admin, testProject2, auxUser);
    }

    @Test(expected = IllegalStateException.class)
    public void unauthorizedUserAddMember() {
        ps.addUserToProject(auxUser, testProject, admin);
    }

    @Test(expected = IllegalStateException.class)
    public void addMemberTwice() {
        User newMember = us.create("New Member", "password", "a@a.com");
        ps.addUserToProject(admin, testProject, newMember);
        ps.addUserToProject(admin, testProject, newMember);
    }

    @Test
    public void addUserToProject() {
        User newMember = us.create("anotherUser", "password", "a@a.com");
        ps.addUserToProject(admin, testProject, newMember);
        assertTrue(ps.getProjectMembers(testProject).contains(newMember));
    }

    @Test(expected = IllegalStateException.class)
    public void removeUserFromInexistentProject() {
        ps.addUserToProject(admin, testProject, auxUser);
        ps.deleteProject(admin, testProject);
        ps.deleteUserFromProject(admin, testProject, auxUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNullUser() {
        ps.deleteUserFromProject(admin, testProject, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAdminRemoveMember() {
        ps.addUserToProject(admin, testProject, auxUser);
        ps.deleteUserFromProject(null, testProject, auxUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserFromNullProject() {
        ps.addUserToProject(admin, null, auxUser);
    }

    @Test(expected = IllegalStateException.class)
    public void unauthorizedMembershipRemoval() {
        ps.addUserToProject(admin, testProject, auxUser);
        ps.deleteUserFromProject(auxUser, testProject, admin);
    }

    @Test(expected = IllegalStateException.class)
    public void removeMyselfFromProject() {
        ps.deleteUserFromProject(admin, testProject, admin);
    }

    @Test
    public void removeUserFromProject() {
        ps.addUserToProject(admin, testProject, auxUser);
        boolean addedUser = ps.getProjectMembers(testProject).contains(auxUser);
        ps.deleteUserFromProject(admin, testProject, auxUser);
        assertTrue(addedUser && !ps.getProjectMembers(testProject).contains(auxUser));
    }

    @Test(expected = IllegalArgumentException.class)
    public void userBelongsToNullProject() {
        ps.userBelongsToProject(null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullUserBelongsToProject() {
        ps.userBelongsToProject(testProject, null);
    }

    @Test
    public void userBelongsToInexistentProject() {
        assertFalse(ps.userBelongsToProject(testProject, auxUser));
    }

    @Test
    public void userBelongsToProject() {
        assertTrue(ps.getProjectMembers(testProject).contains(admin));
    }

    @Test
    public void userDoesntBelongToProject() {
        assertFalse(ps.getProjectMembers(testProject).contains(auxUser));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getProjectsForNullUser() {
        ps.getProjectsForUser(null);
    }

    @Test
    public void getProjectForUser() {
        assertEquals(testProject, ps.getProjectsForUser(admin).get(0));
    }

    @Test(expected = IllegalStateException.class)
    public void getProjectByRandomId() {
        ps.getProjectById(2500);
    }
}
