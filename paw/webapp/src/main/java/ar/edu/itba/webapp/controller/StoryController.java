package ar.edu.itba.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

@Controller
@RequestMapping("/project/{project}/iteration/{iteration}/story")
public class StoryController {
	
	@Autowired
	TaskService ts;

	@Autowired
	StoryService ss;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource() {
		final ModelAndView mav = new ModelAndView("story/new");
		return mav;
	}
	
	@RequestMapping(value = "/{story}", method = RequestMethod.GET, name = "story.getById")
	public ModelAndView getResource(@ModelAttribute("projectId") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		final ModelAndView mav = new ModelAndView("story/taskList");
		final Story story = ss.getById(storyId);
		final List<Task> tasks = ts.getTasksForStory(story);
		mav.addObject("tasks", tasks);
		mav.addObject("story", story);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource(@ModelAttribute("projectId") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

	@RequestMapping(value = "/{story}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@ModelAttribute("projectId") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

	@RequestMapping(value = "/{story}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@ModelAttribute("projectId") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		return null;
	}

}