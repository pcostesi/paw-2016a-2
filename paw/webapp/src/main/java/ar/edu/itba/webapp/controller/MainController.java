package ar.edu.itba.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.project.ProjectService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	ProjectService ps;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getResource() {
		final ModelAndView mav = new ModelAndView("main/projectList");
		mav.addObject("projDetailsList", ps.getProjectDetailList());
		return mav;
	}
	
}