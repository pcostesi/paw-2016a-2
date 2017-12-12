package ar.edu.itba.webapp.form.constraint;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        firstFieldName = constraintAnnotation.start();
        secondFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final LocalDate firstDate = (LocalDate) PropertyUtils.getProperty(value, firstFieldName);
            final LocalDate secondDate = (LocalDate) PropertyUtils.getProperty(value, secondFieldName);

            if (secondDate.compareTo(firstDate) < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } catch (final Exception e) {
            return true;
        }
    }
}