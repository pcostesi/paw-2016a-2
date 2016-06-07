package ar.edu.itba.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

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
	private User user;

	@Autowired
	private UserService us;

	@Before
	public void setup() {
		if (!us.usernameExists(name)) {
			user = us.create(name, password, mail);
		}
		user = us.getByUsername(name);
	}

	@Test
   	public void createUserTest() {
		assertNotNull(user);
   	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithNullName(){
		us.create(null, password, mail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void creatWithNullPassword(){
		us.create(name, null, mail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithNullMail(){
		us.create(name, password, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void createWithUsedName(){
		us.create(name, password, unusedMail);
	}
	
	@Test(expected = IllegalStateException.class)
	public void createWithUsedMail(){
		us.create(unusedName, password, mail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithEmptyPassword(){
		us.create(unusedName, empty, unusedMail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithEmptyName(){
		us.create(empty, password, unusedMail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithEmptyMail(){
		us.create(unusedName, password, empty);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithLongName(){
		us.create(longString, password, unusedMail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithLongPassword(){
		us.create(unusedName , longString, unusedMail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createWithLongMail(){
		us.create(unusedName, password, longString + unusedMail);
	}	
	
	@Test
	public void getByUsernameTest(){
		assertTrue(us.getByUsername(user.username()).equals(user));
	}
	
	@Test(expected = IllegalStateException.class)
	public void getByinvalidUserName(){
		us.getByUsername("Fake it!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getByusernameWithNull(){
		us.getByUsername(null);
	}
	
	@Test
	public void getUsernamesTest(){
		int i;
		for(i = 100; i < 1100; i++){
			us.create(String.valueOf(i), password, String.valueOf(i) + "@" + String.valueOf(i) + ".com");
		}
		assertTrue(us.getUsernames().size() == 1001); //+1 due to @Before
	}
	
	@Test
	public void usernameExistsTest(){
		assertTrue(us.usernameExists(user.username()));
		assertFalse(us.usernameExists("fake"));
	}
	
	@Test
	public void usernameExistsWithNull(){
		assertTrue(us.emailExists(user.mail()));
		assertFalse(us.emailExists("fake"));	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emailExistsTest(){
		us.emailExists(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emailExistsWithNull(){
		us.usernameExists(null);
	}
	
}