package adec.tech.atms.domain;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/17/2016
 *         Time: 5:28 PM
 */

public class AssignedMilestoneActivity {

    private Integer id;
    private AssignedMilestone assignedMilestone;
    private Task task;
    private String dateTimeStart;
    private String dateTimeEnd;
    private Double totalTime;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AssignedMilestone getAssignedMilestone() {
        return assignedMilestone;
    }

    public void setAssignedMilestone(AssignedMilestone assignedMilestone) {
        this.assignedMilestone = assignedMilestone;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
