package adec.tech.atms.web.rest;

import adec.tech.atms.config.Constants;
import adec.tech.atms.domain.Milestone;
import adec.tech.atms.domain.Status;
import adec.tech.atms.repository.MilestoneRepository;
import adec.tech.atms.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/9/2016
 *         Time: 4:44 PM
 */

@RestController
@RequestMapping("api/v1/projects")
public class SampleMilestoneRepository {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private StatusRepository statusRepository;

    @RequestMapping(
            value = "{id}/milestones",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getMilestoneList(
            @PathVariable("id") String id,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber
    ) {

        int projectId;

        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_PROJECT_ID_FORMAT,
                    HttpStatus.BAD_REQUEST);
        }

        int page;

        try {
            page = Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_PAGE_NUMBER,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                milestoneRepository.getProjectMilestones(projectId, searchQuery, page)
                , HttpStatus.OK);
    }

    @RequestMapping(
            value = "{id}/milestones",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> newMilestone(
            @PathVariable("id") String id,
            @Valid @RequestBody Milestone milestone
    ) {

        int projectId;

        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_PROJECT_ID_FORMAT,
                    HttpStatus.BAD_REQUEST);
        }

        int milestoneId = milestoneRepository.createMilestone(milestone, projectId);

        return new ResponseEntity<>(
                milestoneRepository.getMilestoneById(milestoneId)
        , HttpStatus.OK);
    }

    @RequestMapping(
            value = "{id}/milestones/{milestoneId}",
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> updateMilestone(
            @Valid @RequestBody Milestone milestone
    ) {

        milestoneRepository.updateMilestone(milestone);

        return new ResponseEntity<>(milestoneRepository.getMilestoneById(milestone.getId()), HttpStatus.OK);
    }

    @RequestMapping(
            value = "{id}/milestones/{milestoneId}",
            method = RequestMethod.PATCH
    )
    public ResponseEntity<?> setMilestoneStatus(
            @PathVariable("milestoneId") String milestoneId,
            @RequestBody String status
    ) {

        int v;

        try {
            v = Integer.parseInt(milestoneId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(Constants.INVALID_MILESTONE_ID_FORMAT, HttpStatus.BAD_REQUEST);
        }

        String milestoneStatus;
        Status statusByLabel;

        try {

            switch (Constants.Status.valueOf(status)) {
                case ACTIVE:
                    milestoneStatus = Constants.Status.ACTIVE.toString();
                    break;
                case INACTIVE:
                    milestoneStatus = Constants.Status.INACTIVE.toString();
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            statusByLabel = statusRepository.getStatusByLabel(milestoneStatus);

            if(statusByLabel == null)
                throw new IllegalArgumentException();

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Constants.INVALID_MILESTONE_STATUS, HttpStatus.BAD_REQUEST);
        }

        milestoneRepository.setMilestoneStatus(v, statusByLabel);

        return new ResponseEntity<>(milestoneRepository.getMilestoneById(v), HttpStatus.OK);
    }

}
