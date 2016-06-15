package ar.edu.itba.webapp.controller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.interfaces.service.TranslationService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.auth.ScrumlrUserDetails;

@Controller
public abstract class BaseController {
	final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private UserService us;
	
	@Autowired
	private TranslationService ts;
	
	@ModelAttribute
	public Locale currentLanguage() {
		return LocaleContextHolder.getLocale();
	}
	
	@ModelAttribute
	public List<Entry<String, String>> breadcrumb(final HttpServletRequest request) {
		final List<Entry<String, String>> result = new ArrayList<>();
		final String tag = ts.getMessage("breadcrumb.home");
		final String url = request.getContextPath();
		result.add(new AbstractMap.SimpleEntry<String, String>(tag, url));
		logger.debug("Home called, {}", result);
		return result;
	}
	
	@ModelAttribute
	public User user() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username;
		if (auth == null || !auth.isAuthenticated()) {
			return null;
		}
		final Object principal = auth.getPrincipal();
		if (principal instanceof ScrumlrUserDetails) {
			return ((ScrumlrUserDetails) principal).getUser();
		} else if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		try {
			return us.getByUsername(username);

		} catch (final IllegalStateException e) {
			logger.debug("No username {}.", username, e);
			return null;
		}
	}
}
