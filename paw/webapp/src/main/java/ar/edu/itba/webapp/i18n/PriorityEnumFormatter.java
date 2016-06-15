package ar.edu.itba.webapp.i18n;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import ar.edu.itba.models.Priority;

public class PriorityEnumFormatter implements Formatter<Priority> {

	@Autowired
	private MessageSource messageSource; 
	
	@Override
	public String print(Priority object, Locale locale) {
		return messageSource.getMessage("models.priority." + object.name().toLowerCase(), null, locale);
	}

	@Override
	public Priority parse(String text, Locale locale) throws ParseException {    	
	    for(Priority test : Priority.values()) {
	    	if(test.name().equalsIgnoreCase(text)) {
	    		return test;
	    	}
	    }
	    return null;
	}

}
