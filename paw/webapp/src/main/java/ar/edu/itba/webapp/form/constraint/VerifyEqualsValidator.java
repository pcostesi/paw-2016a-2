package ar.edu.itba.webapp.form.constraint;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class VerifyEqualsValidator implements ConstraintValidator<VerifyEquals, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final VerifyEquals constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final String password = (String) PropertyUtils.getProperty(value, firstFieldName);
            final String verifyPassword = (String) PropertyUtils.getProperty(value, secondFieldName);
            if (!password.equals(verifyPassword)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(firstFieldName).addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } catch (final Exception e) {
            return true;
        }
    }
}