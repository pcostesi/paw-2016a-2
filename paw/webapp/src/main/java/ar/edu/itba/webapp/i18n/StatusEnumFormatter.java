package ar.edu.itba.webapp.i18n;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Status;

public class StatusEnumFormatter implements Formatter<Status> {

	@Autowired
	private MessageSource messageSource; 
	
	@Override
	public String print(Status object, Locale locale) {
		return messageSource.getMessage("models.status." + object.name().toLowerCase(), null, locale);
	}

	@Override
	public Status parse(String text, Locale locale) throws ParseException {    	
	    for(Status test : Status.values()) {
	    	if(test.name().equalsIgnoreCase(text)) {
	    		return test;
	    	}
	    }
	    return null;
	}

}
