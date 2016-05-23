package adec.tech.atms.web.rest;

import adec.tech.atms.config.Constants;
import adec.tech.atms.domain.AssignedMilestone;
import adec.tech.atms.domain.Task;
import adec.tech.atms.repository.ActivityRepository;
import adec.tech.atms.repository.MilestoneRepository;
import adec.tech.atms.repository.ProjectRepository;
import adec.tech.atms.repository.TaskRepository;
import adec.tech.atms.web.rest.dto.MilestoneTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Jonathan Leijendekker Date: 2/10/2016 Time: 2:44 PM
 */

@RestController
@RequestMapping("api/v1/home")
public class HomeResource {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(
            value = "activity/in",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> taskIn(@Valid @RequestBody MilestoneTaskDTO milestoneTaskDTO) {

        // TASK IN THE MILESTONE
        activityRepository.taskIn(Integer.parseInt(milestoneTaskDTO.getAssignedMilestoneId()),
                Integer.parseInt(milestoneTaskDTO.getTaskId()));

        return new ResponseEntity<>(activityRepository.getCurrentActivityByUser(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "activity/out",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<?> taskOut(@RequestBody(required = false) String milestoneActivityId) {

        if (milestoneActivityId == null || milestoneActivityId.trim().length() == 0)
            return new ResponseEntity<>(Constants.INVALID_ACTIVITY_ID_FORMAT, HttpStatus.BAD_REQUEST);

        int v;

        try {
            v = Integer.parseInt(milestoneActivityId);
        } catch (Exception e) {
            return new ResponseEntity<>(Constants.INVALID_ACTIVITY_ID_FORMAT, HttpStatus.BAD_REQUEST);
        }

        // TASK OUT THE ACTIVITY
        String message = activityRepository.taskOut(v);
        if (!message.equals(Constants.SUCCESS))
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(Constants.SUCCESSFULLY_TASKED_OUT, HttpStatus.OK);
    }

    @RequestMapping(
            value = "activity/pause",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> taskPause(
            @RequestBody String milestoneActivityId
    ) {

        if (milestoneActivityId == null || milestoneActivityId.trim().length() == 0)
            return new ResponseEntity<>(Constants.INVALID_ACTIVITY_ID_FORMAT, HttpStatus.BAD_REQUEST);

        int v;

        try {
            v = Integer.parseInt(milestoneActivityId);
        } catch (Exception e) {
            return new ResponseEntity<>(Constants.INVALID_ACTIVITY_ID_FORMAT, HttpStatus.BAD_REQUEST);
        }

        // TASK OUT THE ACTIVITY
        String message = activityRepository.taskOut(v);
        if (!message.equals(Constants.SUCCESS))
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        AssignedMilestone assignedMilestone = activityRepository.getAssignedMilestoneByActivityId(v);

        if (assignedMilestone == null)
            return new ResponseEntity<>(Constants.INVALID_ACTIVITY, HttpStatus.OK);

        // TASK IN BREAK MILESTONE
        activityRepository.taskIn(assignedMilestone.getId(), 1);

        return new ResponseEntity<>(activityRepository.getCurrentActivityByUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "currentactivity", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentActivity() {

        return new ResponseEntity<>(activityRepository.getCurrentActivityByUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getTasks() {

        List<Task> tasks = taskRepository.getUserTasks();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @RequestMapping(
            value = "lastprojects",
            method = RequestMethod.GET,
            produces = {
                    MediaType.TEXT_PLAIN_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<?> getLastProjects(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber
    ) {

        int v;

        try {
            v = Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_PAGE_NUMBER,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(projectRepository.getLastProjectsByUser(v), HttpStatus.OK);
    }

    @RequestMapping(
            value = "assignedmilestones",
            method = RequestMethod.GET,
            produces = {
                    MediaType.TEXT_PLAIN_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<?> getAssignedMilestones(
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber) {

        int v;

        try {
            v = Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_PAGE_NUMBER,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(milestoneRepository.getAssignedMilestones(searchQuery, v),
                HttpStatus.OK);
    }

}
