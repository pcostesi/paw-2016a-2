package ar.edu.itba.webapp.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.service.TranslationService;

@Service
public class TranslationServiceImpl implements TranslationService {
	@Autowired
	private MessageSource messageSource; 
	
	public String getMessage(final String id, final Object... objects) {
		final Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, objects, locale);
	}
}