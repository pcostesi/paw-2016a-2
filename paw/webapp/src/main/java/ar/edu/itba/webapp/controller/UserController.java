package ar.edu.itba.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.webapp.form.UserForm;


@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService us;
	
	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		return new ModelAndView("user/login");
	}
	
	@RequestMapping(value = "/user/new", method = RequestMethod.GET)
	public ModelAndView getRegisterForm(@ModelAttribute("userForm") UserForm userForm) {
		return new ModelAndView("user/newUser");
	}
	
	@RequestMapping(value = "/user/new", method = RequestMethod.POST)
	public ModelAndView postRegisterForm(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
		
		if (result.hasErrors()) {
			return new ModelAndView("user/newUser");
		} else {
			us.create(userForm.getUser(), userForm.getPassword(), userForm.getMail());
			return new ModelAndView("user/login");
		}
		
	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ModelAndView me() {
		return new ModelAndView("user/profile");
	}
}
