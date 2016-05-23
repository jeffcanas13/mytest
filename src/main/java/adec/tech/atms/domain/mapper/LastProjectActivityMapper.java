package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LastProjectActivityMapper implements RowMapper<AssignedMilestoneActivity> {

    @Override
    public AssignedMilestoneActivity mapRow(ResultSet rs, int i) throws SQLException {
        return new AssignedMilestoneActivity() {
            {
                setId(rs.getInt("assigned_milestone_activity.id"));
                setAssignedMilestone(new AssignedMilestone() {
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
                        setDetails(rs.getString("assigned_milestone.details"));
                        setDueDate(rs.getString("assigned_milestone.due_date"));
                        setDuration(rs.getDouble("assigned_milestone.duration"));
                        setAssignedDate(rs.getString("assigned_milestone.assigned_date"));
                    }
                });
                setTask(new Task() {
                    {
                        setId(rs.getInt("task.id"));
                        setCode(rs.getString("task.code"));
                        setName(rs.getString("task.name"));
                        setDescription(rs.getString("task.description"));
                        setCreatedDate(rs.getString("task.created_date"));
                    }
                });
                setDateTimeStart(rs.getString("assigned_milestone_activity.datetime_start"));
                setDateTimeEnd(rs.getString("assigned_milestone_activity.datetime_end"));
                setTotalTime(rs.getDouble("assigned_milestone_activity.total_time"));
                setRemarks(rs.getString("assigned_milestone_activity.remarks"));
            }
        };
    }
}
