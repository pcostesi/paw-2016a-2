package ar.edu.itba.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project/{project}/iteration")
public class IterationController {
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource() {
		final ModelAndView mav = new ModelAndView("iteration/newIteration");
		return mav;
	}
	
	@RequestMapping(value = "/{iteration}", method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable String project,
			@PathVariable String iteration) {
		final ModelAndView mav = new ModelAndView("iteration/taskList");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource() {
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@PathVariable String id) {
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@PathVariable String id) {
		return null;
	}

}