package adec.tech.atms.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import adec.tech.atms.config.Constants;
import adec.tech.atms.validation.validator.VerticalValidator;

@Documented
@Constraint(validatedBy = {VerticalValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVertical {
	
	String message() default "{" + Constants.PACKAGE_NAME + ".validation.annotation.ValidVertical}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
