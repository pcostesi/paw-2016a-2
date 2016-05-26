package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.UserService;

public class UserExistsValidator implements ConstraintValidator<UserExists, String> {
	
	@Autowired
    private UserService us;
	
    @Override
    public void initialize(final UserExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
  		return us.usernameExists(username);
    }

}