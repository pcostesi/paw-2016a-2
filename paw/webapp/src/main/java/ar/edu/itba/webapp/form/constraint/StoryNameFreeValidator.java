package ar.edu.itba.webapp.form.constraint;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.webapp.form.StoryForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class StoryNameFreeValidator implements ConstraintValidator<StoryNameFree, StoryForm> {

    @Autowired
    private StoryService ss;

    @Autowired
    private IterationService is;

    private String markedFieldName;

    @Override
    public void initialize(final StoryNameFree constraintAnnotation) {
        markedFieldName = constraintAnnotation.markedField();
    }

    @Override
    public boolean isValid(final StoryForm form, final ConstraintValidatorContext context) {
        if (form.getOldTitle().equals(form.getTitle())) {
            return true;
        }
        final Iteration iteration = is.getIterationById(form.getIterationId());
        if (ss.storyExists(iteration, form.getTitle())) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(markedFieldName).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}