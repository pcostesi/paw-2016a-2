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
import org.springframework.web.util.UriComponentsBuilder;

import ar.edu.itba.interfaces.BacklogService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.form.BacklogForm;

@Controller
@RequestMapping("/project/{projectCode}/backlog")
public class BacklogController extends BaseController {

	@Autowired
	private ProjectService ps;
	
	@Autowired
	private BacklogService bs;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView createResourceGet(@PathVariable String projectCode, @ModelAttribute("backlogForm") BacklogForm backlogForm) {
		final Project project = ps.getProjectByCode(projectCode);
		final ModelAndView mav = new ModelAndView("backlog/newItem");
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createResourcePost(@PathVariable String projectCode,
			@Valid @ModelAttribute("backlogForm") BacklogForm backlogForm, BindingResult result) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		if (result.hasErrors()) {
			mav = new ModelAndView("backlog/newItem");
			mav.addObject("project", project);
		} else {
			final String description = backlogForm.getDescription();
			bs.createBacklogItem(project, backlogForm.getTitle(), description.length() == 0 ? null : description);
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName("project.details")
					.arg(0, project.code()).build().replace("/grupo2","");
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{backlogId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable int backlogId) {
		final BacklogItem item = bs.getBacklogById(backlogId);
		bs.deleteBacklogItem(item);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.list").build();
		return new ModelAndView("redirect:" + resourceUrl);
	}
	
}
