package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProjectServiceImplTest {

	@Autowired
	private ProjectService ps;
	
	@Autowired
	private UserService us;

	private String pName = "Test Project";
	private String pDesc = "This project was created for testing purpouses";
	private String pCode = "tp";
	private User admin;
	private Set<User> members;
	
	private Project testProject;

	@Before
	public void setup() {
		if (us.usernameExists("testName")) {
			admin = us.getByUsername("testName");
		} else {
			admin = us.create("testName", "testPass", "test@mail.com");			
		}
		members = new HashSet<User>();
		testProject = ps.createProject(admin, members, pName, pDesc, pCode);
	}
	
	@After
	public void endingSetup(){
		if(ps.projectCodeExists(pCode)){
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
}
