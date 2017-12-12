package ar.edu.itba.webapp.i18n;

import ar.edu.itba.models.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class PriorityEnumFormatter implements Formatter<Priority> {

    @Autowired
    private final MessageSource messageSource;

    public PriorityEnumFormatter(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String print(final Priority object, final Locale locale) {
        final String message = messageSource.getMessage("models.priority." + object.name().toLowerCase(), null, locale);
        if (message == null) {
            return object.toString().replace('_', ' ');
        }
        return message;
    }

    @Override
    public Priority parse(final String text, final Locale locale) throws ParseException {
        for (final Priority test : Priority.values()) {
            if (test.name().equalsIgnoreCase(text)) {
                return test;
            }
        }
        return null;
    }

}
