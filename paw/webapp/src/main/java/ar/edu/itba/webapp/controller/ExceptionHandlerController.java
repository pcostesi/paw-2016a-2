package ar.edu.itba.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
	
	 @ExceptionHandler(value = {Exception.class, RuntimeException.class})
	    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) {
		 	final ModelAndView mav = new ModelAndView("error/serviceFail");

	        mav.addObject("exception", exception);
	        return mav;
	    }
	 
}
