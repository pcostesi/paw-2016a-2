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
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.IterationForm;
import ar.edu.itba.webapp.form.TaskForm;

@Controller
@RequestMapping(value = "/project/{projectCode}/iteration/{iterationId}/story/{storyId}/task")
public class TaskController {

	@Autowired
	TaskService ts;

	@Autowired
	StoryService ss;
	
	@Autowired
	IterationService is;
	
	@Autowired
	ProjectService ps;
	
	@Autowired
	UserService us;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@PathVariable final String projectCode,
			@PathVariable final int iterationId,
			@PathVariable final int storyId,
			@ModelAttribute("taskForm") TaskForm taskForm) {
		final ModelAndView mav = new ModelAndView("task/newTask");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Story story = ss.getById(storyId);
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("story", story);
		
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createNewResource(@PathVariable final String projectCode,
			@PathVariable final int iterationId,
			@PathVariable final int storyId,
			@Valid @ModelAttribute("taskForm") final TaskForm taskForm,
			BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Story story = ss.getById(storyId);
		if (result.hasErrors()) {
			mav = new ModelAndView("task/newTask");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
			mav.addObject("story", story);
		} else {
			final Task task = ts.createTask(story, taskForm.getTitle(), taskForm.getDescription(), taskForm.getStatus(), taskForm.getOwner(), taskForm.getScore());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
					.arg(0, projectCode)
					.arg(1, iterationId)
					.build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{taskId}/edit", method = RequestMethod.GET)
	public ModelAndView getModifyResource(@ModelAttribute("taskForm") TaskForm taskForm,
			@PathVariable int storyId, 
			@PathVariable int iterationId,
			@PathVariable String projectCode,
			@PathVariable int taskId) {
		final ModelAndView mav = new ModelAndView("task/editTask");
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Story story = ss.getById(storyId);
		final Task task = ts.getTaskById(taskId);
		taskForm.setDescription(task.getDescription());
		taskForm.setOwner(task.getOwner());
		taskForm.setScore(task.getScore());
		taskForm.setStatus(task.getStatus());
		taskForm.setTitle(task.getTitle());
		mav.addObject("project", project);
		mav.addObject("iteration", iteration);
		mav.addObject("story", story);
		mav.addObject("task", task);
		return mav;
	}

	@RequestMapping(value = "/{taskId}/edit", method = RequestMethod.POST)
	public ModelAndView editResource(@Valid @ModelAttribute("taskForm") TaskForm taskForm, BindingResult result,
			@PathVariable String projectCode,
			@PathVariable int iterationId,
			@PathVariable int storyId,
			@PathVariable int taskId) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		final Iteration iteration = is.getIterationById(iterationId);
		final Task task = ts.getTaskById(taskId);
		if (result.hasErrors()) {
			mav = new ModelAndView("iteration/editIteration");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
		} else {
			ts.changeOwnership(task, taskForm.getOwner());
			ts.changeStatus(task, taskForm.getStatus());
			ts.changeScore(task, taskForm.getScore());
			ts.changeTitle(task, taskForm.getTitle());
			ts.changeDescription(task, taskForm.getDescription());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
					.arg(0, projectCode)
					.arg(1, iterationId)
					.build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{taskId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable String projectCode,
			@PathVariable int iterationId,
			@PathVariable int storyId,
			@PathVariable int taskId) {
		Task task = ts.getTaskById(taskId);
		ts.deleteTask(task);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("iteration.details")
				.arg(0, projectCode)
				.arg(1, iterationId)
				.build();
		return new ModelAndView("redirect:" + resourceUrl);
	}
}