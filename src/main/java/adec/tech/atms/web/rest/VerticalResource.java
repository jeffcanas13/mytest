package adec.tech.atms.web.rest;

import adec.tech.atms.config.Constants;
import adec.tech.atms.domain.Vertical;
import adec.tech.atms.repository.VerticalRepository;
import adec.tech.atms.web.rest.dto.VerticalStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/verticals")
public class VerticalResource {

	@Autowired
	private VerticalRepository verticalRepository;
	
	@RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
	public ResponseEntity<?> saveVertical(@Valid @RequestBody Vertical verticals){
			verticalRepository.saveVertical(verticals);

		return new ResponseEntity<>(Constants.SUCCESSFULLY_CREATED_VERTICAL, HttpStatus.OK);
	}
	
	@RequestMapping(
            method = RequestMethod.GET
    )
	public ResponseEntity<?> getVerticals(){
//			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber
//	) {
//
//	        int v;
//
//	        try {
//	            v = Integer.parseInt(pageNumber);
//	        } catch (NumberFormatException e) {
//	            return new ResponseEntity<>(new ResponseDTO(Constants.INVALID_PAGE_NUMBER),
//	                    HttpStatus.BAD_REQUEST);
//	        }

//	        return new ResponseEntity<>(new ResponseDTO(verticalRepository.getVerticals(v)), HttpStatus.OK);
		
	        return new ResponseEntity<>(verticalRepository.getVerticalsList(), HttpStatus.OK);
	}


	@RequestMapping(
            value = "status",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
	public ResponseEntity<?> inactivate(@Valid @RequestBody VerticalStatusDTO verticalStatusDTO) {
		
		Integer verticalId = Integer.parseInt(verticalStatusDTO.getVerticalId());
		Integer statusId = Integer.parseInt(verticalStatusDTO.getStatusId());
		
		if (verticalRepository.getStatus(verticalId) == statusId) {
			if (Constants.Status.getStatus(statusId) == Constants.Status.ACTIVE)
				return new ResponseEntity<>(Constants.VERTICAL_ACTIVATION_FAILED, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<>(Constants.VERTICAL_INACTIVATION_FAILED, HttpStatus.BAD_REQUEST);
		}

		String message = verticalRepository.setStatus(verticalId, statusId);
		
		if (!message.equals(Constants.SUCCESS))
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(Constants.SUCCESS, HttpStatus.OK);
	}
}