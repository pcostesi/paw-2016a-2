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
@RequestMapping("/project/{project}/iteration/{iteration}/task")
public class TaskController {
	
	@Autowired
	TaskService ts;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getNewResource(@ModelAttribute("taskForm") TaskForm taskForm) {
		final ModelAndView mav = new ModelAndView("task/newTask");
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createNewResource(@Valid @ModelAttribute("taskForm") TaskForm taskForm, BindingResult result) {
		final ModelAndView mav;
		if (result.hasErrors()) {
			mav = new ModelAndView("task/newTask");
		} else {
			mav = new ModelAndView("redirect:/");
		}
		return mav;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("task");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveResource() {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView modifyResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteResource(@PathVariable String id) {
		final ModelAndView mav = new ModelAndView("helloworld");
		return mav;
	}

}