package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.auth.ScrumlrUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public abstract class BaseController {
	final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private UserService us;

	public User getLoggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username;
		if (auth == null || !auth.isAuthenticated()) {
			return null;
		}
		final Object principal = auth.getPrincipal();
		if (principal instanceof ScrumlrUserDetails) {
			return ((ScrumlrUserDetails) principal).getUser();
		} else if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		try {
			return us.getByUsername(username);

		} catch (final IllegalStateException e) {
			logger.debug("No username {}.", username, e);
			return null;
		}
	}

	@XmlRootElement
	protected static class ErrorMessage {
		@XmlElement
		public String message;

		@XmlElement
		public String status;


		public static ErrorMessage asError(String status, String desc){
			ErrorMessage message = new ErrorMessage();
			message.status = status;
			message.message = desc;
			return message;
		}
	}
}
