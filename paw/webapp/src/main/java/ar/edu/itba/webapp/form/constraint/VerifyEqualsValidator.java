package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

public class VerifyEqualsValidator implements ConstraintValidator<VerifyEquals, Object> {

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
			String password = (String) PropertyUtils.getProperty(value, firstFieldName);
			String verifyPassword = (String) PropertyUtils.getProperty(value, secondFieldName);
			if (!password.equals(verifyPassword)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(firstFieldName).addConstraintViolation();
				return false;
			} else {
				return true;
			}  
		}catch (Exception e) {
			return true;
		}
	}
}