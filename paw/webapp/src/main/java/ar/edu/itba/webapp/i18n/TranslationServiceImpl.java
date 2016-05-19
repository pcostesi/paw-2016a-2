package ar.edu.itba.webapp.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TranslationServiceImpl {
	@Autowired
	private MessageSource messageSource; 
	
	public String getMessage(String id, Object... objects) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, objects, locale);
	}
}