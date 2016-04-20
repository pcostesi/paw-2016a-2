package ar.edu.itba.webapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.iteration.IterationService;
import ar.edu.itba.interfaces.project.ProjectService;
import ar.edu.itba.interfaces.task.TaskService;

@Controller
public class TestController {
  
  @Autowired
  private ProjectService ps;
  
  @Autowired
  private IterationService is;
  
  @Autowired
  private TaskService ts;
  
  @RequestMapping("/test")
  public ModelAndView test() {
          final ModelAndView mav = new ModelAndView("user");
          
          ps.createProject("Test", "This is a test project");
          is.createIteration("Test", new Date(), new Date());      
          ts.createTask("Test", 1, "My first task", "Hello 1st task!");
          ts.createTask("Test", 1, "My second task", "Hello 2nd task!");

          return mav;
  }

}