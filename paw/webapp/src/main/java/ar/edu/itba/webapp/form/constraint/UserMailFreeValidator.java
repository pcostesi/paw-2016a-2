package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class UserMailFreeValidator implements ConstraintValidator<UserMailFree, String> {

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