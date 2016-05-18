package ar.edu.itba.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import ar.edu.itba.models.User;
import junit.framework.TestCase;

public class EmailServiceImplTest extends TestCase {

	private EmailServiceImpl es;
	private GreenMail greenMail;

    @Before
    public void setUp() {
    	greenMail = new GreenMail(ServerSetupTest.SMTP);
    	greenMail.start();
    	Session session = greenMail.getSmtp().createSession();
    	es = new EmailServiceImpl(session);
    }
    
    @After
    public void tearDown() {
    	es = null;
        greenMail.stop();
    }
    
    private User randomUser() {
    	User mockedUser = Mockito.mock(User.class);
    	Mockito.when(mockedUser.mail()).thenReturn("pcostesi@pcostesi.me");
    	Mockito.when(mockedUser.username()).thenReturn("AlumnoAlAzar");
    	Mockito.when(mockedUser.password()).thenReturn("Diaz4Evah");
    	return mockedUser;
    }

    @Test
    public void testCanSendEmail() throws MessagingException {
    	final String subject = "test subject";
    	final String body = "test body";
    	final User user = randomUser();
    	
    	//the method we want to test
    	es.notifyUser(user, subject, body);
    	
    	//we have one and only one mail
    	assertEquals(1, greenMail.getReceviedMessagesForDomain(user.mail()).length);
    	
    	//The body is body and the subject is subject
    	Message message = greenMail.getReceviedMessagesForDomain(user.mail())[0];
    	assertEquals(body, GreenMailUtil.getBody(message));
    	assertEquals(subject, message.getSubject());
    }
}