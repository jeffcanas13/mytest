package adec.tech.atms.domain;

public class AssignedMilestone {

	private Integer id;
	private Milestone milestone;
	private User assignedTo;
	private String details;
	private String dueDate;
	private Double duration;
	private AssignedMilestoneStatus assignedMilestoneStatus;
	private User assignedBy;
	private String assignedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public AssignedMilestoneStatus getAssignedMilestoneStatus() {
        return assignedMilestoneStatus;
    }

    public void setAssignedMilestoneStatus(AssignedMilestoneStatus assignedMilestoneStatus) {
        this.assignedMilestoneStatus = assignedMilestoneStatus;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }
}
