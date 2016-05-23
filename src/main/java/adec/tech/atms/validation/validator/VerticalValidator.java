package adec.tech.atms.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import adec.tech.atms.config.Constants;
import adec.tech.atms.repository.VerticalRepository;
import adec.tech.atms.validation.annotation.ValidVertical;

public class VerticalValidator implements ConstraintValidator<ValidVertical, String>{

	@Autowired
	private VerticalRepository verticalRepository;
	
	@Override
	public void initialize(ValidVertical constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		
		int v = Integer.parseInt(value);
		
		if(!verticalRepository.isVerticalExisting(v)) {
			context.buildConstraintViolationWithTemplate(Constants.VERTICAL_DOES_NOT_EXIST).addConstraintViolation();
			return false;
		}
		
		
		
		return true;
	}
}
