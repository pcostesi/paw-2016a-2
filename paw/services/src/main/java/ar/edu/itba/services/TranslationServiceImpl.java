package ar.edu.itba.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.TranslationService;

@Service
public class TranslationServiceImpl implements TranslationService{
	
	@Autowired
	private MessageSource messageSource; 
	
	public String getMessage(String id, Object... objects) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, objects, locale);
	}
}