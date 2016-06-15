package ar.edu.itba.webapp.i18n;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import ar.edu.itba.models.Score;

public class ScoreEnumFormatter implements Formatter<Score> {

    @Autowired
    public MessageSource messageSource;

    public ScoreEnumFormatter(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String print(final Score object, final Locale locale) {
        final String message = messageSource.getMessage("models.score." + object.name().toLowerCase(), null, locale);
        if (message == null) {
            return object.toString().replace('_', ' ');
        }
        return message;
    }

    @Override
    public Score parse(final String text, final Locale locale) throws ParseException {
        for(final Score test : Score.values()) {
            if(test.name().equalsIgnoreCase(text)) {
                return test;
            }
        }
        return null;
    }

}
