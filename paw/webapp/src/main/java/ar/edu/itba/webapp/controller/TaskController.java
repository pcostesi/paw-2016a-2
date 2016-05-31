package ar.edu.itba.webapp.controller;

import java.util.List;
import java.util.Optional;

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
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.form.TaskForm;

@Controller
@RequestMapping(value = "/project/{projectCode}/iteration/{iterationId}/story/{storyId}/task")
public class TaskController extends BaseController {

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
		final List<String> users = us.getUsernamesForProject(project);
		mav.addObject("users",users);
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
			final List<String> users = us.getUsernamesForProject(project);
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
			mav.addObject("story", story);			
			mav.addObject("users",users);
		} else {
			final User owner = (taskForm.getOwner().isEmpty())? null : us.getByUsername(taskForm.getOwner());
			final String description = (taskForm.getDescription().isEmpty())? null : taskForm.getDescription();
			ts.createTask(story, taskForm.getTitle(), description, taskForm.getStatus(), owner, taskForm.getScore(), taskForm.getPriority());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "iteration.details")
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
		final Optional<User> owner = task.owner();
		final String ownerUsername = (!owner.isPresent())? null: owner.get().username();
		final List<String> users = us.getUsernamesForProject(project);
		taskForm.setDescription(task.description().isPresent()? task.description().get() : null);
		taskForm.setOwner(ownerUsername);
		taskForm.setScore(task.score());
		taskForm.setStatus(task.status());
		taskForm.setTitle(task.title());
		taskForm.setPriority(task.priority());
		mav.addObject("users", users);
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
			final Story story = ss.getById(storyId);
			final List<String> users = us.getUsernamesForProject(project);
			mav = new ModelAndView("task/editTask");
			mav.addObject("project", project);
			mav.addObject("iteration", iteration);
			mav.addObject("task", task);
			mav.addObject("users", users);
			mav.addObject("story", story);
		} else {
			final User owner = (taskForm.getOwner().isEmpty())? null : us.getByUsername(taskForm.getOwner());
			final String description = (taskForm.getDescription().length() == 0)? null : taskForm.getDescription();			
			ts.changeOwnership(task, owner);
			ts.changeStatus(task, taskForm.getStatus());
			ts.changeScore(task, taskForm.getScore());
			ts.changeTitle(task, taskForm.getTitle());
			ts.changeDescription(task, description);
			ts.changePriority(task, taskForm.getPriority());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "iteration.details")
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
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "iteration.details")
				.arg(0, projectCode)
				.arg(1, iterationId)
				.build();
		return new ModelAndView("redirect:" + resourceUrl);
	}
}