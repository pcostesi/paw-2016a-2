package ar.edu.itba.webapp.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
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

import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.interfaces.service.TranslationService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.IterationForm;

@Controller
@RequestMapping("/project/{projectCode}/iteration")
public class IterationController extends BaseController {
	
	@Autowired
	private ProjectService ps;
	
	@Autowired
	private StoryService ss;
	
	@Autowired
	private IterationService is;
	
	@Autowired
	private BacklogService bs;
	
	@Autowired
	private TranslationService ts;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@ModelAttribute("iterationForm") final IterationForm iterationForm, @PathVariable final String projectCode) {
		final ModelAndView mav = new ModelAndView("iteration/newIteration");
		final Project project = ps.getProjectByCode(projectCode);
		final Integer iterationToInheritFrom = is.getLastFinishedIterationNumber(project);
		final Map<String, String> durationTranslation = getDurationMap();
		iterationForm.setBeginDate(LocalDate.now());
		mav.addObject("iterationToInheritFrom", iterationToInheritFrom);
		mav.addObject("durationOptions", durationTranslation);
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView postNewResource(@PathVariable("projectCode") final String projectCode,
			@Valid @ModelAttribute("iterationForm") final IterationForm iterationForm, final BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		if (result.hasErrors()) {
			mav = new ModelAndView("iteration/newIteration");	
			final Integer iterationToInheritFrom = is.getLastFinishedIterationNumber(project);		
			final Map<String, String> durationTranslation = getDurationMap();
			mav.addObject("iterationToInheritFrom", iterationToInheritFrom);
			mav.addObject("durationOptions", durationTranslation);
			mav.addObject("project", project);
		} else {
			if (!iterationForm.getInheritIteration()) {
				is.createIteration(project, iterationForm.getBeginDate(), iterationForm.getEndDate());				
			} else {
				is.createIteration(project, iterationForm.getBeginDate(), iterationForm.getEndDate(), iterationForm.getIterationNumberToInherit());
			}
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
					.arg(0, projectCode).build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	private Map<String, String> getDurationMap() {
		final Map<String, String> durationTranslation = new LinkedHashMap<String, String>();
		durationTranslation.put("1WEEKS", ts.getMessage("iteration.1weeks"));
		durationTranslation.put("2WEEKS", ts.getMessage("iteration.2weeks"));
		durationTranslation.put("3WEEKS", ts.getMessage("iteration.3weeks"));
		durationTranslation.put("4WEEKS", ts.getMessage("iteration.4weeks"));
		durationTranslation.put("CUSTOM", ts.getMessage("iteration.custom"));
		return durationTranslation;
	}
	@RequestMapping(value = "/{iterationId}", method = RequestMethod.GET, name="iteration.details")
	public ModelAndView getResource(@PathVariable final String projectCode,
			@PathVariable("iterationId") final int iterationId) {
		final ModelAndView mav = new ModelAndView("iteration/storyList");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Map<Story, List<Task>> stories = ss.getStoriesWithTasksForIteration(iteration);
		final List<BacklogItem> backlog = bs.getBacklogForProject(project);
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("stories", stories);
		mav.addObject("backlog", backlog);
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/edit", method = RequestMethod.GET)
	public ModelAndView getModifyResource(@PathVariable final String projectCode, @PathVariable final int iterationId,
			@ModelAttribute("iterationForm") final IterationForm iterationForm) {
		final ModelAndView mav = new ModelAndView("iteration/editIteration");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		iterationForm.setBeginDate(iteration.startDate());
		iterationForm.setEndDate(iteration.endDate());
		iterationForm.setProjectId(project.projectId());
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/edit", method = RequestMethod.POST)
	public ModelAndView postModifyResource(@PathVariable final String projectCode, @PathVariable final int iterationId,
			@Valid @ModelAttribute("iterationForm") final IterationForm iterationForm, final BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		if (result.hasErrors()) {
			mav = new ModelAndView("iteration/editIteration");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
		} else {
			is.setDates(iteration, iterationForm.getBeginDate(), iterationForm.getEndDate());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
					.arg(0, projectCode).build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}

	@RequestMapping(value = "/{iterationId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable final String projectCode, @PathVariable final int iterationId) {
		final Iteration iteration = is.getIterationById(iterationId);
		is.deleteIteration(iteration);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
				.arg(0, projectCode).build();
		return new ModelAndView("redirect:" + resourceUrl);
	}

}