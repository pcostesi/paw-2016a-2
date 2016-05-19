package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProjectServiceImplTest {

	@Autowired
	private ProjectService ps;

	private String pName = "Test Project";
	private String pDesc = "This project was created for testing purpouses";
	private String pCode = "tp";
	
	Project testProject;

	@Before
	@Transactional
	public void setup() {
		testProject = ps.createProject(pName, pDesc, pCode);
	}
	
	@After
	@Transactional
	public void endingSetup(){
		if(ps.projectCodeExists(pCode)){
			ps.deleteProject(ps.getProjectByCode(pCode));
		}
	}

	@Test
	@Transactional
	public void createProjectTest() {
		assertNotNull("Project should not be null", testProject);
	}

	@Test
	@Transactional
	public void getProjectTest() {
		assertNotNull("Project should not be null", ps.getProjectByCode(pCode));
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void deleteProjectTest() {
		ps.deleteProject(testProject);
		ps.getProjectByCode(pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createNullNameProject() {
		ps.createProject(null, pDesc, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createNullDescProject() {
		ps.createProject(pName, null, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void createNullCodeProject() {
		ps.createProject(pName, pDesc, null);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void createExistingProject() {
		ps.createProject(pName, pDesc, pCode);
		ps.createProject(pName, pDesc, pCode);
		ps.createProject(pName, pDesc, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void getProjectWithNull() {
		ps.getProjectByCode(null);
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void getInexistingProject() {
		ps.getProjectByCode("fakecode");
	}

	@Test(expected = IllegalStateException.class)
	@Transactional
	public void doubleDeleteTest() {
		ps.deleteProject(ps.getProjectByCode(pCode));
		ps.deleteProject(ps.getProjectByCode(pCode));
	}

	@Test
	@Transactional
	public void setNewName() {
		String newName = "Super Name";
		testProject = ps.setName(testProject, newName);
		assertSame("should be same", testProject.name(), newName);
	}

	@Test
	@Transactional
	public void setNewDescrpition() {
		testProject = ps.setDescription(testProject, "This is the projects new description");
		assertSame("should be same", testProject.description(), "This is the projects new description");
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void setInvalidName() {
		ps.setName(testProject, "");
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void setInvalidLongName() {
		ps.setName(testProject, "asdasdasj kjsabfksbaf kj dbs fkljdsbfkl jsdb fkjsdbflksdjbfiewif bB FKDB KfbIEBUfbeeufib DifbsIFBo bbDuf yufs busdybf sidyfbysudfb xcv jbesui");
	}

	@Test
	@Transactional
	public void theIntensiveTester() {
		int i;
		Project subject;
		for (i = 0; i < 300; i++) {
			subject = ps.createProject("Inte", "nsive", "tester");
			ps.deleteProject(subject);
		}
		subject = ps.createProject("Inte", "nsive", "tester");
		assertNotNull("It should be ok", subject);
	}

	@Test
	@Transactional
	public void theIntensiveTesterSDeluxeEdition() {
		int i;
		String string;
		for (i = 0; i < 700; i++) {
			string = String.valueOf(i);
			ps.createProject(string, string, string);
		}
		for (i = 0; i < 700; i++) {
			string = String.valueOf(i);
			ps.deleteProject(ps.getProjectByCode(string));
		}
	}

	@Test
	@Transactional
	public void setCodeTest() {
		Project testMonkey;
		try {
			ps.setCode(testProject, null);
			fail("Exception not thrown when code was null");
		} catch (Exception e) {
		}

		try {
			ps.setCode(testProject, "");
			fail("Exception not thrown when code was empty");
		} catch (Exception e) {
		}

		try {
			ps.setCode(testProject, "djsdjfsldkfjsldkfjslkdfjsdlkfjskldfjsldkf");
			fail("Exception not thrown when code was to long");
		} catch (Exception e) {
		}

		try {
			ps.setCode(testProject, "ASJKDbaS Kdhsa bfasjdh");
			fail("Exception not thrown when code was invalid");
		} catch (Exception e) {
		}

		try {
			ps.setCode(null, "tcc");
			fail("Exception not thrown when project was null");
		} catch (Exception e) {
		}

		try {
			testMonkey = ps.createProject(pName + "a", pDesc + "a", pCode + "a");
			ps.deleteProject(testMonkey);
			ps.setCode(testMonkey, "tcc");
			fail("Exception not thrown when project was inexistent");
		} catch (Exception e) {
		}

		testMonkey = ps.createProject("setCodeTester", "Idem", "sc");
		testMonkey = ps.setCode(testMonkey, "nc");		
		assertSame("Code should be nc, but it was not", testMonkey.code(), "nc");
		ps.deleteProject(testMonkey);
	}

	@Test
	@Transactional
	public void getProjectByIdTest() {
		testProject = ps.createProject("getProjectTester", "Shalalalal", "gpt");
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
	@Transactional
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
