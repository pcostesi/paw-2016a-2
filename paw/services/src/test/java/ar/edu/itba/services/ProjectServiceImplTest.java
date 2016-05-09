package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class ProjectServiceImplTest {

	@Autowired
	private ProjectServiceImpl ps;

	private String pName = "Test Project";
	private String pDesc = "This project was created for testing purpouses";
	private String pCode = "tp";

	public Project testProject;

	@Before
	public void setup() {
		System.out.println(ps == null);	
	}

	/*
	 * @Test public void test(){ System.out.println(dao == null);
	 * System.out.println("hola gator"); System.out.println(ds == null);
	 * //System.out.println(ps != null); }
	 */

	@Test
	public void createProjectTest() {
		assertNotNull("Project should not be null", ps.createProject(pName, pDesc, pCode));
	}

	@Test
	public void getProjectTest() {
		assertNotNull("Project should not be null", ps.getProjectByCode(pCode));
	}

	@Test(expected = IllegalStateException.class)
	public void deleteProjectTest() {
		ps.deleteProject(ps.getProjectByCode(pCode));
		ps.getProjectByCode(pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createNullNameProject() {
		ps.createProject(null, pDesc, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createNullDescProject() {
		ps.createProject(pName, null, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createNullCodeProject() {
		ps.createProject(pName, pDesc, null);
	}

	@Test(expected = IllegalStateException.class)
	public void createExistingProject() {
		ps.createProject(pName, pDesc, pCode);
		ps.createProject(pName, pDesc, pCode);
		ps.createProject(pName, pDesc, pCode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getProjectWithNull() {
		ps.getProjectByCode(null);
	}

	@Test(expected = IllegalStateException.class)
	public void getNullProject() {
		ps.getProjectByCode("fake code");
	}

	@Test(expected = IllegalStateException.class)
	public void doubleDeleteTest() {
		ps.deleteProject(ps.getProjectByCode(pCode));
		ps.deleteProject(ps.getProjectByCode(pCode));
	}

	@Test
	public void setNewName() {
		testProject = ps.createProject(pName, pDesc, pCode);
		testProject = ps.setName(testProject, "This is the projects new name");
		assertSame("should be same", testProject.name(), "This is the projects new name");
	}

	@Test
	public void setNewDescrpition() {
		testProject = ps.createProject(pName, pDesc, pCode);
		testProject = ps.setDescription(testProject, "This is the projects new description");
		assertSame("should be same", testProject.description(), "This is the projects new name");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidName() {
		ps.setName(testProject, "");
	}

	@Test
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
	public void setCodeTest() {
		testProject = ps.createProject("setCodeTester", "Idem", "sc");

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
			ps.deleteProject(testProject);
			ps.setCode(testProject, "tcc");
			fail("Exception not thrown when project was inexistent");
		} catch (Exception e) {
		}

		testProject = ps.createProject("setCodeTester", "Idem", "sc");

		testProject = ps.setCode(testProject, "nc");
		assertSame("Should be nc", testProject.code(), "nc");
	}

	@Test
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
	public void getProjectByCodeTest() {
		String code = "gpbct";

		testProject = ps.createProject("getProjectByCodeTester", "Shalalalal", code);
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
		assertNotNull("project should not be null", ps.getProjectByCode(code));
	}
}
