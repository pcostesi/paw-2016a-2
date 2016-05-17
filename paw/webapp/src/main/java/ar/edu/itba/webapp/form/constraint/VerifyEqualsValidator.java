package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ar.edu.itba.webapp.form.UserForm;

public class VerifyEqualsValidator implements ConstraintValidator<VerifyEquals, UserForm> {
	
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final VerifyEquals constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final UserForm value, final ConstraintValidatorContext context) {
            if (!value.getPassword().equals(value.getVerifyPassword())) {
            	context.disableDefaultConstraintViolation();
            	context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
            	context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(firstFieldName).addConstraintViolation();
            	return false;
            } else {
            	return true;
            }       
    }
}