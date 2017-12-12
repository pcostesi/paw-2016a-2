package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class UserUsernameFreeValidator implements ConstraintValidator<UserUsernameFree, String> {

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