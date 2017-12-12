package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.EmailService;
import ar.edu.itba.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Sending an email in java is a kilombow
 */
@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final Session session;
    private final String HOST = "smtp.pcostesi.me";
    private final String PORT = "25";
    private final String FROM = "scrumlr@pcostesi.me";
    private final String USER = "scrumlr";
    private final String PASS = "scrumlrrocks";
    private final Properties props = new Properties();


    public EmailServiceImpl() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        final Authenticator auth = getAuthenticator(USER, PASS);
        session = Session.getInstance(props, auth);
    }

    public EmailServiceImpl(final Session session) {
        this.session = session;
    }

    private static Authenticator getAuthenticator(final String user, final String pass) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };
    }

    private Message createNewMessage(final String from, final String to, final String subject, final String body) throws MessagingException {
        final Message msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        try {
            msg.setFrom(new InternetAddress(FROM, "Scrumlr"));
        } catch (final UnsupportedEncodingException e) {
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
    public boolean notifyUser(final User user, final String subject, final String body) {
        try {
            final Message message = createNewMessage(FROM, user.mail(), subject, body);
            logger.debug("Sending message {}", message);
            Transport.send(message);
        } catch (final MessagingException e) {
            logger.error("Message delivery for {} failed", user, e);
            return false;
        }
        return true;
    }

}
