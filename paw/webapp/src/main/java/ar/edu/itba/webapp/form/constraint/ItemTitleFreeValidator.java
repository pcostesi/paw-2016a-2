package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.BacklogService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.form.BacklogForm;

public class ItemTitleFreeValidator implements ConstraintValidator<ItemTitleFree, BacklogForm> {

	@Autowired
    private BacklogService bs;
	
	@Autowired
	private ProjectService ps;
	
	private String markedFieldName;
	
    @Override
    public void initialize(final ItemTitleFree constraintAnnotation) {
    	markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final BacklogForm form, final ConstraintValidatorContext context) {
    	final Project project = ps.getProjectById(form.getProjectId());
    	if (bs.titleIsUsed(project, form.getTitle())) {
    		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
    		return false;
    	} else {
    		return true;
    	}
    }
}