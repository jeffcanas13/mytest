package adec.tech.atms.web.rest;

import adec.tech.atms.config.Constants;
import adec.tech.atms.repository.ActivityRepository;
import adec.tech.atms.repository.MilestoneRepository;
import adec.tech.atms.repository.ProjectRepository;
import adec.tech.atms.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectResource {

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping(
            method = RequestMethod.GET,
            produces = {
                    MediaType.TEXT_PLAIN_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
	public ResponseEntity<?> getProjectList(
			@RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber) {

		int v;

		try {
			v = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(Constants.INVALID_PAGE_NUMBER,
					HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(projectRepository.getProjectList(searchQuery,v),
				HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<ResponseDTO> createProject(
//			@RequestBody Project project) {
//		
//		
//		
//		return null;
//	}
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.GET)
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.POST)
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	

}
