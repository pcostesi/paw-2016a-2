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
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.TaskForm;

@Controller
@RequestMapping(value = "/project/{project}/iteration/{iteration}/story/{story}/task")
public class TaskController {

	@Autowired
	TaskService ts;

	@Autowired
	StoryService ss;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@ModelAttribute("taskForm") TaskForm taskForm) {
		final ModelAndView mav = new ModelAndView("task/newTask");
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createNewResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId,
			@Valid @ModelAttribute("taskForm") final TaskForm taskForm,
			BindingResult result) {
		final ModelAndView mav;
		final Story story = ss.getById(storyId);
		if (result.hasErrors()) {
			mav = new ModelAndView("task/newTask");
		} else {
			final Task task = ts.createTask(story, taskForm.getTitle(), taskForm.getDescription());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("story.getById")
					.arg(0, projectId)
					.arg(1, iterationId)
					.arg(2, storyId)
					.build();
			mav = new ModelAndView("redirect:" + resourceUrl + "?task=" + task.getTaskId());
		}
		return mav;
	}
	
	@RequestMapping(value = "/{task}/edit", method = RequestMethod.POST)
	public ModelAndView saveResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{task}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId,
			@ModelAttribute("taskId") @PathVariable("task") int taskId) {
		try {
			final Task task = ts.getTaskById(taskId);
			ts.deleteTask(task);
		} catch (Exception e) {
			
		}
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("story.getById")
				.arg(0, projectId)
				.arg(1, iterationId)
				.arg(2, storyId)
				.build();
		return new ModelAndView("redirect:" + resourceUrl + "?task=" + taskId);
	}
}