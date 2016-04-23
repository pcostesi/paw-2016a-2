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

import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.webapp.form.TaskForm;

@Controller
@RequestMapping(value = "/project/{project}/iteration/{iteration}/story/{story}/task")
public class TaskController {
	
	@Autowired
	TaskService ts;
	
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
		final ModelAndView mav = new ModelAndView("task/newTask");
//		if (result.hasErrors()) {
//			mav = new ModelAndView("task/newTask");
//		} else {
//			final Task task = ts.createTask(projectId, iterationId, taskForm.getTitle(), taskForm.getDescription());
//			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("task.getById")
//					.arg(0, task.getTaskId())
//					.buildAndExpand(projectId, iterationId);
//			mav = new ModelAndView("redirect:" + resourceUrl);
//		}
		return mav;
	}
	
	@RequestMapping(value = "/{task}", method = RequestMethod.GET, name = "task.getById")
	public ModelAndView getResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId,
			@ModelAttribute("taskId") @PathVariable("task") int taskId,
			@Valid @ModelAttribute("taskForm") final TaskForm taskForm,
			BindingResult result) {
		final ModelAndView mav = new ModelAndView("task/task");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{task}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId,
			@ModelAttribute("taskId") @PathVariable("task") int taskId) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{task}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@ModelAttribute("projectName") @PathVariable("project") final String projectId,
			@ModelAttribute("iterationId") @PathVariable("iteration") final int iterationId,
			@ModelAttribute("storyId") @PathVariable("story") final int storyId,
			@ModelAttribute("taskId") @PathVariable("task") int taskId) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}
}