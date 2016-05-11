package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.webapp.form.ProjectForm;

public class ProjectCodeFreeValidator implements ConstraintValidator<ProjectCodeFree, ProjectForm> {
	
	@Autowired
    private ProjectService ps;

	private String markedFieldName;
	
    @Override
    public void initialize(final ProjectCodeFree constraintAnnotation) {
    	markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final ProjectForm form, final ConstraintValidatorContext context) {
    	if (form.getCode().equals(form.getOldCode())) {
    		return true;
    	}
    	boolean projectCodeExists = ps.projectCodeExists(form.getCode());	    	
    	if (projectCodeExists) {
    		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
    		return false;
    	} else {
    		return true;
    	}
    }
}