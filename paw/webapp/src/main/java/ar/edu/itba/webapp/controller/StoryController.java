package ar.edu.itba.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project/{project}/iteration/{iteration}/story")
public class StoryController {
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource() {
		final ModelAndView mav = new ModelAndView("story/new");
		return mav;
	}
	
	@RequestMapping(value = "/{story}", method = RequestMethod.GET)
	public ModelAndView getResource(@ModelAttribute("projectId") @PathVariable("project") final int projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		final ModelAndView mav = new ModelAndView("story/list");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource(@ModelAttribute("projectId") @PathVariable("project") final int projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

	@RequestMapping(value = "/{story}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@ModelAttribute("projectId") @PathVariable("project") final int projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

	@RequestMapping(value = "/{story}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@ModelAttribute("projectId") @PathVariable("project") final int projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

}