package ar.edu.itba.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserServices;
import ar.edu.itba.models.User;
import ar.edu.itba.services.UserServicesImpl;

@Controller
public class UserController {

	@Autowired
	private UserServices service;
	
  @RequestMapping("/")
  public ModelAndView helloWorld() {
	  User testUser = service.create("HOLA", "MUNDO");
      final ModelAndView mav = new ModelAndView("helloworld");      
      mav.addObject("greeting", testUser.getUsername()+":"+testUser.getPassword());
      return mav;
  }
  
  @RequestMapping("/users/{username}")
  public ModelAndView getUser(@PathVariable final String username) {
          final ModelAndView mav = new ModelAndView("user");
          mav.addObject("user", service.getByUsername(username));
          return mav;
  }

}