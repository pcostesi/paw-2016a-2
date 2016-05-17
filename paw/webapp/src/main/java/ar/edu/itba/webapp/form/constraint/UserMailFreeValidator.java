package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.UserService;

public class UserMailFreeValidator implements ConstraintValidator<UserMailFree, String> {
	
	@Autowired
    private UserService us;
	
    @Override
    public void initialize(final UserMailFree constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String mail, final ConstraintValidatorContext context) {
  		return !us.emailExists(mail);
    }

}