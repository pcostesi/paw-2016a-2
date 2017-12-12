package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.webapp.form.ProjectForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class ProjectNameFreeValidator implements ConstraintValidator<ProjectNameFree, ProjectForm> {

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
        final boolean projectNameExists = ps.projectNameExists(form.getName());
        if (projectNameExists) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}