package ar.edu.itba.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;

@Controller
@RequestMapping("/project/{projectCode}/iteration")
public class IterationController {
	
	@Autowired
	private ProjectService ps;
	
	@Autowired
	private StoryService ss;
	
	@Autowired
	private IterationService is;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@PathVariable String projectCode) {
		final ModelAndView mav = new ModelAndView("iteration/newIteration");
		final Project project = ps.getProjectByCode(projectCode);
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value = "/{iterationNumber}", method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable String projectCode,
			@PathVariable("iterationNumber") @ModelAttribute("number") int iterationNumber) {
		final ModelAndView mav = new ModelAndView("iteration/storyList");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIteration(project, iterationNumber);
		final List<Story> stories = ss.getStoriesForIteration(iteration);
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("stories", stories);
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