package adec.tech.atms.domain;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/8/2016
 *         Time: 4:15 PM
 */
public class AssignedMilestoneStatus {

    private Integer id;
    private String label;
    private Integer order;
    private Status status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
