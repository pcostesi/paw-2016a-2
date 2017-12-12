package ar.edu.itba.webapp.form.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ProjectCodeFreeValidator.class)
@Documented
public @interface ProjectCodeFree {
    String message() default "Project code has been used already";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String markedField();

    @Target( {TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ProjectCodeFree[] value();
    }
}