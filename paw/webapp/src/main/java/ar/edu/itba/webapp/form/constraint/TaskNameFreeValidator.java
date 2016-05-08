package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.webapp.form.TaskForm;

public class TaskNameFreeValidator implements ConstraintValidator<TaskNameFree, TaskForm> {
	
	@Autowired
	private StoryService ss;
	
	@Autowired
	private TaskService ts;
	
	private String markedFieldName;
	
    @Override
    public void initialize(final TaskNameFree constraintAnnotation) {
    	markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final TaskForm form, final ConstraintValidatorContext context) {
    	try {
    		if (form.getOldTitle().equals(form.getTitle())) {
    			return true;
    		}    		
    		final Story story = ss.getById(form.getStoryId());
    		if (ts.taskNameExists(story, form.getTitle())) {
    			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
    			return false;
    		} else {
    			return true;
    		}
    	} catch(IllegalStateException exception) {
    		return true;
    	}
    }
}