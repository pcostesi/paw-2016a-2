package ar.edu.itba.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController extends BaseController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		return new ModelAndView("user/login");
	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ModelAndView me() {
		return new ModelAndView("user/profile");
	}
}
