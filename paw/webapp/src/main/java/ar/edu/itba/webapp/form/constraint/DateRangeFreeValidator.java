package ar.edu.itba.webapp.form.constraint;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.form.IterationForm;

public class DateRangeFreeValidator implements ConstraintValidator<DateRangeFree, IterationForm> {
	
	@Autowired
	private IterationService is;
	
	@Autowired
	private ProjectService ps;
	
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final DateRangeFree constraintAnnotation) {
        firstFieldName = constraintAnnotation.start();
        secondFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(final IterationForm form, final ConstraintValidatorContext context) {
        try {
            final LocalDate startDate = form.getBeginDate();
            final LocalDate endDate = form.getEndDate();
            final Project project = ps.getProjectById(form.getProjectId());
            boolean isInvalid = false;
            if(form.getIterationId() < 0) {
            	isInvalid = !is.isValidDateRangeInProject(project, null, startDate, endDate);
            } else {
            	isInvalid = !is.isValidDateRangeInProject(project, is.getIterationById(form.getIterationId()), startDate, endDate);
            }
            
            if (isInvalid) {
            	context.disableDefaultConstraintViolation();
            	context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(firstFieldName).addConstraintViolation();
            	context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
            	return false;
            } else {
            	return true;
            }
        }
        catch (final Exception e) {
        	return true;
        }        
    }
}