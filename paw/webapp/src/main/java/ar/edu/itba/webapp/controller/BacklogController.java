package ar.edu.itba.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project/{projectCode}/iteration/{iteration}/backlog")
public class BacklogController extends BaseController {

	@RequestMapping(value = "/new")
	public ModelAndView backlogCreateItem() {
		final ModelAndView mav = new ModelAndView("backlog/new");
		return mav;
	}
}
