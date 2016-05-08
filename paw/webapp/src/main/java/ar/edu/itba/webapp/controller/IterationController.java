package ar.edu.itba.webapp.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.util.UriComponentsBuilder;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.IterationForm;

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
	public ModelAndView getNewResource(@ModelAttribute("iterationForm") IterationForm iterationForm, @PathVariable String projectCode) {
		final ModelAndView mav = new ModelAndView("iteration/newIteration");
		final Project project = ps.getProjectByCode(projectCode);
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView postNewResource(@PathVariable("projectCode") final String projectCode,
			@Valid @ModelAttribute("iterationForm") IterationForm iterationForm, BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		if (result.hasErrors()) {
			mav = new ModelAndView("iteration/newIteration");
			mav.addObject(project);
		} else {			
			is.createIteration(project, iterationForm.getBeginDate(), iterationForm.getEndDate());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
					.arg(0, projectCode).build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{iterationId}", method = RequestMethod.GET, name="iteration.details")
	public ModelAndView getResource(@PathVariable String projectCode,
			@PathVariable("iterationId") int iterationId) {
		final ModelAndView mav = new ModelAndView("iteration/storyList");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Map<Story, List<Task>> stories = ss.getStoriesWithTasksForIteration(iteration);
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("stories", stories);
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/edit", method = RequestMethod.GET)
	public ModelAndView getModifyResource(@PathVariable String projectCode, @PathVariable int iterationId,
			@ModelAttribute("iterationForm") IterationForm iterationForm) {
		final ModelAndView mav = new ModelAndView("iteration/editIteration");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		iterationForm.setBeginDate(iteration.startDate());
		iterationForm.setEndDate(iteration.endDate());
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/edit", method = RequestMethod.POST)
	public ModelAndView postModifyResource(@PathVariable String projectCode, @PathVariable int iterationId,
			@Valid @ModelAttribute("iterationForm") IterationForm iterationForm, BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		if (result.hasErrors()) {
			mav = new ModelAndView("iteration/editIteration");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
		} else {
			is.setBeginDate(iteration, iterationForm.getBeginDate());
			is.setEndDate(iteration, iterationForm.getEndDate());
			String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
					.arg(0, projectCode).build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable String projectCode, @PathVariable int iterationId) {
		final Iteration iteration = is.getIterationById(iterationId);
		is.deleteIteration(iteration);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
				.arg(0, projectCode).build();
		return new ModelAndView("redirect:" + resourceUrl);
	}

}