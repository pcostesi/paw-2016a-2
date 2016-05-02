package ar.edu.itba.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Controller
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService us;
	
	@ModelAttribute
	public User loggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			logger.debug("Authentication not supported");
			return null;
		}
		try {
			return us.getByUsername((String) auth.getPrincipal());
		} catch (IllegalStateException e) {
			logger.debug("No username {}", (String) auth.getPrincipal());
			return null;
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		return new ModelAndView("user/login");
	}

	@RequestMapping(value = "/403")
	public ModelAndView accessDenied() {
		final ModelAndView mav = new ModelAndView("user/denied");
		return mav;
	}
}
