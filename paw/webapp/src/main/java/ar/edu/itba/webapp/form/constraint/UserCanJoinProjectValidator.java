package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.form.AddMemberForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class UserCanJoinProjectValidator implements ConstraintValidator<UserCanJoinProject, AddMemberForm> {

    @Autowired
    private ProjectService ps;

    @Autowired
    private UserService us;

    private String markedFieldName;

    @Override
    public void initialize(final UserCanJoinProject constraintAnnotation) {
        markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final AddMemberForm form, final ConstraintValidatorContext context) {
        final Project project = ps.getProjectByCode(form.getProjectCode());
        if (!us.usernameExists(form.getMember()) || ps.userBelongsToProject(project, us.getByUsername(form.getMember()))) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }

}