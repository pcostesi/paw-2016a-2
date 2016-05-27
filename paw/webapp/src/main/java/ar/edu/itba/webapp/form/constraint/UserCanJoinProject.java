package ar.edu.itba.webapp.form.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UserCanJoinProjectValidator.class)
@Documented
public @interface UserCanJoinProject {
    String message() default "User doesn't exist or belongs to the project already";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String markedField();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
    	UserCanJoinProject[] value();
    }
}