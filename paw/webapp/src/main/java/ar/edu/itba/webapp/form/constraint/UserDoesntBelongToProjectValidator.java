package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.form.AddMemberForm;

public class UserDoesntBelongToProjectValidator implements ConstraintValidator<UserDoesntBelongToProject, AddMemberForm> {
	
	@Autowired
    private ProjectService ps;
	
	@Autowired
	private UserService us;
	
	private String markedFieldName;
	
    @Override
    public void initialize(final UserDoesntBelongToProject constraintAnnotation) {
    	markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final AddMemberForm form, final ConstraintValidatorContext context) {
    	Project project = ps.getProjectByCode(form.getProjectCode());
    	User user = us.getByUsername(form.getMember());
    	if (ps.userBelongsToProject(project, user)) {
    		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
    		return false;
    	} else {
    		return true;
    	}
    }

}