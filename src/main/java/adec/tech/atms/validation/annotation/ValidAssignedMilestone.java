package adec.tech.atms.validation.annotation;

import adec.tech.atms.config.Constants;
import adec.tech.atms.validation.validator.AssignedMilestoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/15/2016
 *         Time: 10:42 AM
 */

@Documented
@Constraint(validatedBy = {AssignedMilestoneValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAssignedMilestone {
    String message() default "{" + Constants.PACKAGE_NAME + ".validation.annotation.ValidAssignedMilestone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
