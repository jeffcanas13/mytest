package adec.tech.atms.web.rest.dto;

import adec.tech.atms.config.Constants;
import adec.tech.atms.validation.annotation.ValidAssignedMilestone;
import adec.tech.atms.validation.annotation.ValidTask;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/12/2016
 *         Time: 11:10 AM
 */

@GroupSequence({NotNull.class, Pattern.class, Min.class, Max.class, ValidAssignedMilestone.class, ValidTask.class, MilestoneTaskDTO.class})
public class MilestoneTaskDTO {

    @NotNull(message = Constants.INVALID_MILESTONE_ID_FORMAT, groups = NotNull.class)
    @Pattern(message = Constants.INVALID_MILESTONE_ID_FORMAT, regexp = "\\d+", groups = Pattern.class)
    @Min(message = Constants.INVALID_MILESTONE_ID_FORMAT, value = 1, groups = Min.class)
    @Max(message = Constants.INVALID_MILESTONE_ID_FORMAT, value = Integer.MAX_VALUE, groups = Max.class)
    @ValidAssignedMilestone(groups = ValidAssignedMilestone.class)
    private String assignedMilestoneId;

    @NotNull(message = Constants.SPECIFY_TASK, groups = NotNull.class)
    @Pattern(message = Constants.INVALID_TASK_ID_FORMAT, regexp = "\\d+", groups = Pattern.class)
    @Min(message = Constants.INVALID_TASK_ID_FORMAT, value = 1, groups = Min.class)
    @Max(message = Constants.INVALID_TASK_ID_FORMAT, value = Integer.MAX_VALUE, groups = Max.class)
    @ValidTask(groups = ValidTask.class)
    private String taskId;

    public String getAssignedMilestoneId() {
        return assignedMilestoneId;
    }

    public void setAssignedMilestoneId(String assignedMilestoneId) {
        this.assignedMilestoneId = assignedMilestoneId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}