package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.User;

public interface EmailService {
	
	public boolean notifyUser(final User user, String subject, String body);

}
