package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.webapp.form.ProjectForm;

public class ProjectNameFreeValidator implements ConstraintValidator<ProjectNameFree, ProjectForm> {
	
	@Autowired
    private ProjectService ps;

	private String markedFieldName;
	
    @Override
    public void initialize(final ProjectNameFree constraintAnnotation) {
    	markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final ProjectForm form, final ConstraintValidatorContext context) {
    	if (form.getName().equals(form.getOldName())) {
    		return true;
    	}
    	boolean projectNameExists = ps.projectNameExists(form.getName());	    	
    	if (projectNameExists) {
    		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
    		return false;
    	} else {
    		return true;
    	}
    }
}