package ar.edu.itba.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.auth.ScrumlrUserDetails;

@Controller
public abstract class BaseController {
	final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private UserService us;
	
	@ModelAttribute
	public User user() {
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

		} catch (IllegalStateException e) {
			logger.debug("No username {}.", username, e);
			return null;
		}
	}
}
