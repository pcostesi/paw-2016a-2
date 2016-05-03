package ar.edu.itba.webapp.form.constraint;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import ar.edu.itba.webapp.form.IterationForm;

public class DateRangeValidator implements ConstraintValidator<DateRange, IterationForm> {
	
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final IterationForm value, final ConstraintValidatorContext context) {
        try {
            final Date firstDate = (Date) PropertyUtils.getProperty(value, firstFieldName);
            final Date secondDate = (Date) PropertyUtils.getProperty(value, secondFieldName);
            
            if (secondDate.compareTo(firstDate) < 0) {
            	context.disableDefaultConstraintViolation();
            	context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
            	return false;
            } else {
            	return true;
            }
        }
        catch (final Exception e) {
            //ignore
        }
        return true;
    }
}