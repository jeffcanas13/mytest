package adec.tech.atms.domain;

import adec.tech.atms.config.Constants;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/10/2016
 *         Time: 3:06 PM
 */
public class Milestone {

    private Integer id;

    @NotBlank(message = Constants.MILESTONE_TITLE_REQUIRED)
    @Size(max = 32, message = Constants.MILESTONE_TITLE_MAX)
    private String title;

    @NotNull
    @Size(max = 128, message = Constants.MILESTONE_DESCRIPTION_MAX)
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", message = Constants.MILESTONE_TARGET_HOURS_MIN)
    @DecimalMax(value = "99999.99", message = Constants.MILESTONE_TARGET_HOURS_MAX)
    private Double targetHours;
    private Project project;
    private Status status;
    private User createdBy;
    private String createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Double targetHours) {
        this.targetHours = targetHours;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
