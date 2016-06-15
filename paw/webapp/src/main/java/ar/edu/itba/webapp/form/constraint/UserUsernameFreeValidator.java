package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.webapp.form.UserForm;

public class UserUsernameFreeValidator implements ConstraintValidator<UserUsernameFree, String> {
	
	@Autowired
    private UserService us;
	
    @Override
    public void initialize(final UserUsernameFree constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
  		return !us.usernameExists(username);
    }

}