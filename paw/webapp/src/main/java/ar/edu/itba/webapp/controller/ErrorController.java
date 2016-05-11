package ar.edu.itba.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ErrorController extends BaseController {
	
	@RequestMapping(value = "/403")
	public ModelAndView accessDenied() {
		final ModelAndView mav = new ModelAndView("error/forbidden");
		return mav;
	}
	
	@RequestMapping(value = "/404")
	public ModelAndView notFound() {
		final ModelAndView mav = new ModelAndView("error/notFound");
		return mav;
	}	

	@RequestMapping(value = "/500")
	@ExceptionHandler(Exception.class)
	public ModelAndView internalServerError(HttpServletRequest request, Exception exception) {
		final ModelAndView mav = new ModelAndView("error/internalServerError");
		mav.addObject("exception", exception);
		return mav;
	}
}
