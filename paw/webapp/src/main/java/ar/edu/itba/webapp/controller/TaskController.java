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

import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.TaskForm;

@Controller
@RequestMapping(value = "/project/{project}/iteration/{iteration}/task")
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
			@Valid @ModelAttribute("taskForm") final TaskForm taskForm,
			BindingResult result) {
		final ModelAndView mav;
		if (result.hasErrors()) {
			mav = new ModelAndView("task/newTask");
		} else {
			final Task task = ts.createTask(projectId, iterationId, taskForm.getTitle(), taskForm.getDescription());
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("task.getById")
					.arg(0, task.getTaskId())
					.buildAndExpand(projectId, iterationId);
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET, name = "task.getById")
	public ModelAndView getResource(@PathVariable("taskId") int taskId) {
		final ModelAndView mav = new ModelAndView("task/task");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource() {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@PathVariable int id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@PathVariable int id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}
}