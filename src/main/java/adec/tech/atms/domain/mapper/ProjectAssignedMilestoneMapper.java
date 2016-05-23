package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectAssignedMilestoneMapper implements RowMapper<AssignedMilestone> {

    @Override
    public AssignedMilestone mapRow(ResultSet rs, int i) throws SQLException {

        return new AssignedMilestone() {
            {
                setId(rs.getInt("assigned_milestone.id"));
                setMilestone(new Milestone() {
                    {
                        setId(rs.getInt("milestone.id"));
                        setTitle(rs.getString("milestone.title"));
                        setDescription(rs.getString("milestone.description"));
                        setTargetHours(rs.getDouble("milestone.target_hours"));
                        setProject(new Project() {
                            {
                                setId(rs.getInt("project.id"));
                                setCode(rs.getString("project.code"));
                                setName(rs.getString("project.name"));
                                setDescription(rs.getString("project.description"));
                                setDateStart(rs.getString("project.date_start"));
                                setDateEnd(rs.getString("project.date_end"));
                                setTargetHours(rs.getDouble("project.target_hours"));
                                setClient(rs.getString("project.client"));
                                setCreateDate(rs.getString("project.create_date"));
                            }
                        });
                        setCreatedDate(rs.getString("milestone.created_date"));
                    }
                });
                setAssignedTo(new User() {
                    {
                        setId(rs.getInt("assigned_to.id"));
                        setEmployeeNumber(rs.getString("assigned_to.employee_number"));
                        setFirstName(rs.getString("assigned_to.first_name"));
                        setMiddleName(rs.getString("assigned_to.middle_name"));
                        setLastName(rs.getString("assigned_to.last_name"));
                        setEmail(rs.getString("assigned_to.email"));
                        setLoginAttempts(rs.getInt("assigned_to.login_attempts"));
                        setCreatedDate(rs.getString("assigned_to.created_date"));
                    }
                });
                setDetails(rs.getString("assigned_milestone.details"));
                setDueDate(rs.getString("assigned_milestone.due_date"));
                setDuration(rs.getDouble("assigned_milestone.duration"));
                setAssignedMilestoneStatus(new AssignedMilestoneStatus() {
                    {
                        setId(rs.getInt("assigned_milestone_status.id"));
                        setLabel(rs.getString("assigned_milestone_status.label"));
                        setOrder(rs.getInt("assigned_milestone_status.order"));
                    }
                });
                setAssignedBy(new User() {
                    {
                        setId(rs.getInt("assigned_by.id"));
                        setEmployeeNumber(rs.getString("assigned_by.employee_number"));
                        setFirstName(rs.getString("assigned_by.first_name"));
                        setMiddleName(rs.getString("assigned_by.middle_name"));
                        setLastName(rs.getString("assigned_by.last_name"));
                        setEmail(rs.getString("assigned_by.email"));
                        setLoginAttempts(rs.getInt("assigned_by.login_attempts"));
                        setCreatedDate(rs.getString("assigned_by.created_date"));
                    }
                });
                setAssignedDate(rs.getString("assigned_milestone.assigned_date"));
            }
        };
    }

}
