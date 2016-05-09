package ar.edu.itba.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorController extends BaseController {
	
	@RequestMapping(value = "/403")
	public ModelAndView accessDenied() {
		final ModelAndView mav = new ModelAndView("error/forbidden");
		return mav;
	}
	
	@RequestMapping(value = "/404")
	public ModelAndView notFound() {
		final ModelAndView mav = new ModelAndView("error/not_found");
		return mav;
	}	

	@RequestMapping(value = "/500")
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ModelAndView internalServerError(HttpServletRequest request, Exception exception) {
		final ModelAndView mav = new ModelAndView("error/internal_server_error");
		mav.addObject("exception", exception);

		return mav;
	}
}
