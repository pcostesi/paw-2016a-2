package ar.edu.itba.webapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.TaskService;

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
          
          return mav;
  }

}