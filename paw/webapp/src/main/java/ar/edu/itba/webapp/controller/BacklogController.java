package ar.edu.itba.webapp.controller;

import javax.servlet.http.HttpServletRequest;
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
			@Valid @ModelAttribute("backlogForm") BacklogForm backlogForm, BindingResult result,
			HttpServletRequest request) {
		final ModelAndView mav;
		final Project project = ps.getProjectByCode(projectCode);
		if (result.hasErrors()) {
			mav = new ModelAndView("backlog/newItem");
			mav.addObject("project", project);
		} else {
			final String formDescription = backlogForm.getDescription();
			final String description = (formDescription.length() == 0)? null : formDescription;
			bs.createBacklogItem(project, backlogForm.getTitle(), description);
			final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
					.arg(0, projectCode).build();
			mav = new ModelAndView("redirect:" + resourceUrl);
		}
		return mav;
	}
	
	@RequestMapping(value = "/{backlogId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteResource(@PathVariable String projectCode, @PathVariable int backlogId) {
		final BacklogItem item = bs.getBacklogById(backlogId);
		bs.deleteBacklogItem(item);
		final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.details")
				.arg(0, projectCode).build();
		return new ModelAndView("redirect:" + resourceUrl);
	}
	
}
