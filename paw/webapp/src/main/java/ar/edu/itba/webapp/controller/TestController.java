package ar.edu.itba.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserService;

@Controller
public class TestController {

  @Autowired
  private UserService service;
  
  @RequestMapping("/users/{username}")
  public ModelAndView getUser(@PathVariable final String username) {
          final ModelAndView mav = new ModelAndView("user");
          service.create(username, "bar", "foo@bar.com");
          mav.addObject("user", service.getByUsername(username));
          return mav;
  }

}