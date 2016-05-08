package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.ProjectService;

public class ProjectNameFreeValidator implements ConstraintValidator<ProjectNameFree, String> {
	
	@Autowired
    private ProjectService ps;

    @Override
    public void initialize(final ProjectNameFree constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String name, final ConstraintValidatorContext context) {
    	return !ps.projectNameExists(name);
    }
}