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
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

@Controller
@RequestMapping("/project/{project}/iteration")
public class IterationController {
	
	@Autowired
	private StoryService ss;
	
	@Autowired
	private IterationService is;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource() {
		final ModelAndView mav = new ModelAndView("iteration/newIteration");
		return mav;
	}
	
	@RequestMapping(value = "/{iteration}", method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable String project,
			@PathVariable("iteration") @ModelAttribute("iterationId") int iterationId) {
		final ModelAndView mav = new ModelAndView("iteration/storyList");
		final Iteration iteration = is.getIterationById(iterationId);
		final List<Story> stories = ss.getStoriesForIteration(iteration);
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