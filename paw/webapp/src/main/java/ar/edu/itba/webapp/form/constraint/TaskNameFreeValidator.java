package ar.edu.itba.webapp.form.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.form.TaskForm;

public class TaskNameFreeValidator implements ConstraintValidator<TaskNameFree, TaskForm> {
	
	@Autowired
	private StoryService ss;
	
	@Autowired
	private TaskService ts;
	
	private String titleFieldName;
	
    @Override
    public void initialize(final TaskNameFree constraintAnnotation) {
    	titleFieldName = constraintAnnotation.title();
    }

    @Override
    public boolean isValid(final TaskForm form, final ConstraintValidatorContext context) {
    	try {
    		if (form.getOldTitle().equals(form.getTitle())) {
    			return true;
    		}    		
    		final Story story = ss.getById(form.getStoryId());
    		if (ts.taskNameExists(story, form.getTitle())) {
    			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(titleFieldName).addConstraintViolation();
    			return false;
    		} else {
    			return true;
    		}
    	} catch(IllegalStateException exception) {
    		return true;
    	}
    }
}