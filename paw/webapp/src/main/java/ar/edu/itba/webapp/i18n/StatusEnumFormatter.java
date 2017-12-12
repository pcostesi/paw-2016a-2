package ar.edu.itba.webapp.i18n;

import ar.edu.itba.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class StatusEnumFormatter implements Formatter<Status> {

    @Autowired
    private final MessageSource messageSource;

    public StatusEnumFormatter(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String print(final Status object, final Locale locale) {
        final String message = messageSource.getMessage("models.status." + object.name().toLowerCase(), null, locale);
        if (message == null) {
            return object.toString().replace('_', ' ');
        }
        return message;
    }

    @Override
    public Status parse(final String text, final Locale locale) throws ParseException {
        for (final Status test : Status.values()) {
            if (test.name().equalsIgnoreCase(text)) {
                return test;
            }
        }
        return null;
    }

}
