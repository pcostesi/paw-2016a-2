package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.User;
import ar.edu.itba.UserServices;
import ar.edu.itba.UserServicesImpl;

@Controller
public class HelloWorldController {

  @RequestMapping("/")
  public ModelAndView helloWorld() {
	  final UserServices test = new UserServicesImpl();
	  User testUser = test.create("HOLA", "TROLO");
      final ModelAndView mav = new ModelAndView("helloworld");
      
      mav.addObject("greeting", testUser.getName()+":"+testUser.getPassword());
      return mav;
  }
}