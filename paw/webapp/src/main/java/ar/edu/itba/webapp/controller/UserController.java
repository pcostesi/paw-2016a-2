package ar.edu.itba.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Controller
public class UserController {

	@Autowired
	private UserService us;
	
	@ModelAttribute
	public User loggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		return us.getByUsername((String) auth.getPrincipal());
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		final ModelAndView mav = new ModelAndView("user/login");
		return mav;
	}

	@RequestMapping(value = "/403")
	public ModelAndView accessDenied() {
		final ModelAndView mav = new ModelAndView("user/denied");
		return mav;
	}
}
