package ar.edu.itba.webapp.controller;

import java.util.List;

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

import ar.edu.itba.interfaces.BacklogService;
import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.form.ProjectForm;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService ps;

	@Autowired
	private IterationService is;
	
	@Autowired
	private BacklogService bs;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@ModelAttribute("projectForm") ProjectForm projectForm) {
		final ModelAndView mav = new ModelAndView("project/newProject");
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView postNewResource(@Valid @ModelAttribute("projectForm") ProjectForm projectForm, BindingResult result) {
		final ModelAndView mav;
		if (result.hasErrors()) {
			mav = new ModelAndView("project/newProject");
		} else {
			ps.createProject(projectForm.getName(), projectForm.getDescription(), projectForm.getCode());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"),"project.list").build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{projectCode}", method = RequestMethod.GET, name = "project.details")
	public ModelAndView getResource(@PathVariable String projectCode) {
		final ModelAndView mav = new ModelAndView("project/iterationList");
		final Project project = ps.getProjectByCode(projectCode);
		final List<? extends Iteration> iterations = is.getIterationsForProject(project);
		final List<? extends BacklogItem> backlog = bs.getBacklogForProject(project);
		mav.addObject("iterations", iterations);
		mav.addObject("project", project);
		mav.addObject("backlog", backlog);
		return mav;
	}

	@RequestMapping(value = "/{projectCode}/edit", method = RequestMethod.GET)
	public ModelAndView getModifyResource(@ModelAttribute("projectForm") ProjectForm projectForm, @PathVariable String projectCode) {
		final ModelAndView mav = new ModelAndView("project/editProject");
		final Project project = ps.getProjectByCode(projectCode);
		projectForm.setCode(project.code());
		projectForm.setName(project.name());
		projectForm.setDescription(project.description());
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value = "/{projectCode}/edit", method = RequestMethod.POST)
	public ModelAndView modifyResource(@Valid @ModelAttribute("projectForm") ProjectForm projectForm, BindingResult result,
			@PathVariable String projectCode) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		if (result.hasErrors()) {
			mav = new ModelAndView("project/editProject");
			mav.addObject("project", project);
		} else {
			ps.setCode(project, projectForm.getCode());
			ps.setDescription(project, projectForm.getDescription());
			ps.setName(project, projectForm.getName());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.list").build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}

	@RequestMapping(value = "/{projectCode}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable String projectCode) {
		Project project = ps.getProjectByCode(projectCode);
		ps.deleteProject(project);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.list").build();
		return new ModelAndView("redirect:" + resourceUrl);
	}

}
