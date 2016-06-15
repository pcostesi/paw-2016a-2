package ar.edu.itba.webapp.i18n;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;

public class ScoreEnumFormatter implements Formatter<Score> {

	@Autowired
	private MessageSource messageSource; 
	
	@Override
	public String print(Score object, Locale locale) {
		return messageSource.getMessage("models.score." + object.name().toLowerCase(), null, locale);
	}

	@Override
	public Score parse(String text, Locale locale) throws ParseException {    	
	    for(Score test : Score.values()) {
	    	if(test.name().equalsIgnoreCase(text)) {
	    		return test;
	    	}
	    }
	    return null;
	}

}
