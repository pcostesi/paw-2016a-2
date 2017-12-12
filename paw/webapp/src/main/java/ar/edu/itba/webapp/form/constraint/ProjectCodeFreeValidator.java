package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.webapp.form.ProjectForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class ProjectCodeFreeValidator implements ConstraintValidator<ProjectCodeFree, ProjectForm> {

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
        final boolean projectCodeExists = ps.projectCodeExists(form.getCode());
        if (projectCodeExists) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}