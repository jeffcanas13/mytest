package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.AssignedMilestone;
import adec.tech.atms.domain.AssignedMilestoneActivity;
import adec.tech.atms.domain.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/17/2016
 *         Time: 5:31 PM
 */

public class AssignedMilestoneActivityMapper implements RowMapper<AssignedMilestoneActivity> {
    @Override
    public AssignedMilestoneActivity mapRow(ResultSet rs, int i) throws SQLException {

        return new AssignedMilestoneActivity() {
            {
                setId(rs.getInt("assigned_milestone_activity.id"));
                setAssignedMilestone(new AssignedMilestone() {
                    {
                        setId(rs.getInt("assigned_milestone.id"));
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
