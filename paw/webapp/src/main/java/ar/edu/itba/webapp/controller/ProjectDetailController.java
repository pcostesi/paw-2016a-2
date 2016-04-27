package ar.edu.itba.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

@Controller
@RequestMapping("/project")
public class ProjectDetailController {

	@Autowired
	ProjectService ps;

	@Autowired
	IterationService is;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("project/iterationList");
		final Project project = ps.getProjectByCode(id);
		final List<Iteration> iterations = is.getIterationsForProject(project);
		mav.addObject("iterations", iterations);
		mav.addObject("project", project);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource() {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

}
