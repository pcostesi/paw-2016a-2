package ar.edu.itba.webapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;

@Controller
public class TestController {
  
  @Autowired
  private ProjectService ps;
  
  @Autowired
  private IterationService is;
  
  @RequestMapping("/test")
  public ModelAndView test() {
          final ModelAndView mav = new ModelAndView("user");

          ps.createProject("Test", "This is a test project");
          is.createIteration("Test", new Date(), new Date());

          return mav;
  }

}