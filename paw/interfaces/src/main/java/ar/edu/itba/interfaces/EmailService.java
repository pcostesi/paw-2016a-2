package ar.edu.itba.interfaces;

import ar.edu.itba.models.User;

public interface EmailService {
	
	/**
	 * Send a mail to a user, using its registered mail.
	 * @param user User to send a notification to.
	 * @param subject Mail subject.
	 * @param body Mail content.
	 * @return True if its was sent successfully, false otherwise.
	 */
	public boolean notifyUser(final User user, String subject, String body);

}
