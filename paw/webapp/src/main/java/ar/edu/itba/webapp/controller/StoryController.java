package ar.edu.itba.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.webapp.form.IterationForm;
import ar.edu.itba.webapp.form.StoryForm;

@Controller
@RequestMapping("/project/{projectCode}/iteration/{iterationId}/story")
public class StoryController {
	
	@Autowired
	TaskService ts;

	@Autowired
	StoryService ss;
	
	@Autowired
	ProjectService ps;
	
	@Autowired
	IterationService is;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@ModelAttribute("storyForm") StoryForm storyForm,
			@PathVariable("projectCode") final String projectCode,
			@PathVariable("iterationId") final int iterationId) {
		final ModelAndView mav = new ModelAndView("story/newStory");
		Project project = ps.getProjectByCode(projectCode);
		Iteration iteration = is.getIterationById(iterationId);
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView postNewResource(@PathVariable("projectCode") final String projectCode,
			@PathVariable("iterationId") final int iterationId,
			@Valid @ModelAttribute("storyForm") final StoryForm storyForm,
			BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		if (result.hasErrors()) {
			mav = new ModelAndView("story/newStory");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
		} else {
			final Story story = ss.create(iteration, storyForm.getTitle());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
					.arg(0, projectCode)
					.arg(1, iterationId)
					.build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}

	@RequestMapping(value = "/{storyId}/edit", method = RequestMethod.GET)
	public ModelAndView getModifyResource(@PathVariable int storyId, @PathVariable int iterationId, @PathVariable String projectCode,
			@ModelAttribute("storyForm") StoryForm storyForm) {
		final ModelAndView mav = new ModelAndView("story/editStory");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Story story = ss.getById(storyId);
		storyForm.setTitle(story.getTitle());
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("story", story);
		return mav;
	}

	@RequestMapping(value = "/{storyId}/edit", method = RequestMethod.POST)
	public ModelAndView postModifyResource(@PathVariable String projectCode, @PathVariable int iterationId, @PathVariable int storyId,
			@Valid @ModelAttribute("storyForm") StoryForm storyForm, BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Story story = ss.getById(storyId);
		if (result.hasErrors()) {
			mav = new ModelAndView("story/editIteration");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
		} else {
			ss.setName(story, storyForm.getTitle());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
					.arg(0, projectCode)
					.arg(1, iterationId)
					.build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{storyId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable int storyId, @PathVariable String projectCode, @PathVariable int iterationId) {
		Story story = ss.getById(storyId);
		ss.deleteStory(story);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
				.arg(0, projectCode)
				.arg(1, iterationId)
				.build();
		return new ModelAndView("redirect:" + resourceUrl);
	}
	
}