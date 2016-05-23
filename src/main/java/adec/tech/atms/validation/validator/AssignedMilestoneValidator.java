package adec.tech.atms.validation.validator;

import adec.tech.atms.config.Constants;
import adec.tech.atms.repository.MilestoneRepository;
import adec.tech.atms.validation.annotation.ValidAssignedMilestone;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/15/2016
 *         Time: 8:39 AM
 */

public class AssignedMilestoneValidator implements ConstraintValidator<ValidAssignedMilestone, String> {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    public void initialize(ValidAssignedMilestone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        int v = Integer.parseInt(value);

        if (!milestoneRepository.isAssignedToUser(v)) {
            context.buildConstraintViolationWithTemplate(Constants.MILESTONE_NOT_ASSIGNED).addConstraintViolation();

            return false;
        }

        return true;
    }
}
