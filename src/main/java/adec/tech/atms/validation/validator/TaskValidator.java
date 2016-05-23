package adec.tech.atms.validation.validator;

import adec.tech.atms.config.Constants;
import adec.tech.atms.repository.TaskRepository;
import adec.tech.atms.validation.annotation.ValidTask;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/15/2016
 *         Time: 9:00 AM
 */

public class TaskValidator implements ConstraintValidator<ValidTask, String> {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void initialize(ValidTask constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        int v = Integer.parseInt(value);

        if (!taskRepository.isTaskExisting(v)) {
            context.buildConstraintViolationWithTemplate(Constants.TASK_DOES_NOT_EXIST).addConstraintViolation();

            return false;
        }

        if (v != 1 && !taskRepository.isTaskAssignedToUser(v)) {
            context.buildConstraintViolationWithTemplate(Constants.TASK_NOT_ASSIGNED).addConstraintViolation();

            return false;
        }

        return true;
    }
}
