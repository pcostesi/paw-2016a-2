package ar.edu.itba.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.EmailService;
import ar.edu.itba.models.User;

/**
 * Sending an email in java is a kilombow
 */
@Component
public class EmailServiceImpl implements EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	private Session session;
	private String HOST = "smtp.pcostesi.me";
	private String PORT = "25";
	private String FROM = "scrumlr@pcostesi.me";
	private String USER = "scrumlr";
	private String PASS = "scrumlrrocks";
	private Properties props = new Properties();


	public EmailServiceImpl() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		Authenticator auth = getAuthenticator(USER, PASS);
		session = Session.getInstance(props, auth);
	}

	public EmailServiceImpl(Session session) {
		this.session = session;
	}

	private static Authenticator getAuthenticator(final String user, final String pass) {
		Authenticator auth = new Authenticator() {
			@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };
        return auth;
	}
	
	private Message createNewMessage(final String from, final String to, final String subject, final String body) throws MessagingException {
		final Message msg = new MimeMessage(session);
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
	
        try {
			msg.setFrom(new InternetAddress(FROM, "Scrumlr"));
		} catch (UnsupportedEncodingException e) {
			msg.setFrom(InternetAddress.getLocalAddress(session));
		}
        msg.setReplyTo(InternetAddress.parse(FROM, false));
        
        msg.setSubject(subject);
        msg.setText(body);

        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

		return msg;
	}
	
	@Override
	public boolean notifyUser(User user, String subject, String body) {
		try {
			Message message = createNewMessage(FROM, user.mail(), subject, body);
			logger.debug("Sending message {}", message);
			Transport.send(message);
		} catch (MessagingException e) {
			logger.error("Message delivery for {} failed", user, e);
			return false;
		}
		return true;
	}

}
