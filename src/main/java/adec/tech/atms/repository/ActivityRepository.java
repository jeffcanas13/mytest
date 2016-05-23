package adec.tech.atms.repository;

import adec.tech.atms.config.Constants;
import adec.tech.atms.domain.AssignedMilestone;
import adec.tech.atms.domain.AssignedMilestoneActivity;
import adec.tech.atms.domain.User;
import adec.tech.atms.domain.mapper.AssignedMilestoneActivityMapper;
import adec.tech.atms.domain.mapper.AssignedMilestoneMapper;
import adec.tech.atms.domain.mapper.LastProjectActivityMapper;
import adec.tech.atms.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/22/2016
 *         Time: 1:01 PM
 */

@Repository
public class ActivityRepository extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public int taskIn(int assignedMilestoneId, int taskId) {

        List<AssignedMilestoneActivity> assignedMilestoneActivities = getUserActiveTaskedInActivities();

        if (assignedMilestoneActivities != null && !assignedMilestoneActivities.isEmpty())
            for (AssignedMilestoneActivity assignedMilestone : assignedMilestoneActivities) {
                taskOut(assignedMilestone.getId());
            }

        final String sql =
                "INSERT INTO " +
                        "       assigned_milestone_activity" +
                        "             (" +
                        "              assigned_milestone_id," +
                        "              task_id," +
                        "              datetime_start" +
                        "              ) " +
                        "       VALUES(?, ?, NOW());";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, assignedMilestoneId);
                    ps.setInt(2, taskId);

                    return ps;
                }, keyHolder
        );

        return keyHolder.getKey().intValue();

    }

    public String taskOut(int milestoneActivityId) {

        if (!isActivityNotTaskedOutAndExisting(milestoneActivityId))
            return Constants.MILESTONE_TASK_OUT_FAILED;

        final String sql =
                "UPDATE assigned_milestone_activity " +
                        "       SET datetime_end = NOW(), " +
                        "           total_time = MINUTES_TO_HOURS(" +
                        "                                         datetime_start," +
                        "                                         NOW()" +
                        "                                         ) " +
                        "     WHERE id = ?;";

        getJdbcTemplate().update(sql, milestoneActivityId);

        return Constants.SUCCESS;
    }

    public boolean isActivityNotTaskedOutAndExisting(int milestoneActivityId) {

        User user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return false;

        final String sql =
                "SELECT count(*) " +
                        "           FROM assigned_milestone_activity " +
                        "     INNER JOIN assigned_milestone " +
                        "             ON assigned_milestone.id = assigned_milestone_activity.assigned_milestone_id " +
                        "     INNER JOIN user " +
                        "             ON user.id = assigned_milestone.assigned_to " +
                        "            AND user.status_id = 1 " +
                        "          WHERE user.employee_number = ? " +
                        "            AND assigned_milestone_activity.id = ? " +
                        "            AND assigned_milestone_activity.datetime_end IS NULL;";

        Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, user.getUsername(), milestoneActivityId);

        return count > 0;

    }

    public List<AssignedMilestoneActivity> getUserActiveTaskedInActivities() {

        User user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return null;

        final String sql =
                "SELECT     assigned_milestone_activity.id, " +
                        "   assigned_milestone_activity.datetime_start, " +
                        "   assigned_milestone_activity.datetime_end, " +
                        "   assigned_milestone_activity.total_time, " +
                        "   assigned_milestone_activity.remarks," +
                        "   assigned_milestone.id, " +
                        "   assigned_milestone.details, " +
                        "   assigned_milestone.due_date, " +
                        "   assigned_milestone.duration, " +
                        "   assigned_milestone.assigned_date," +
                        "   task.id, " +
                        "   task.code, " +
                        "   task.name, " +
                        "   task.description, " +
                        "   task.created_date " +
                        "           FROM assigned_milestone_activity " +
                        "     INNER JOIN assigned_milestone " +
                        "             ON assigned_milestone.id = assigned_milestone_activity.assigned_milestone_id" +
                        "     INNER JOIN task " +
                        "             ON task.id = assigned_milestone_activity.task_id " +
                        "     INNER JOIN user " +
                        "             ON user.id = assigned_milestone.assigned_to " +
                        "            AND user.status_id = 1 " +
                        "          WHERE user.employee_number = ? " +
                        "            AND assigned_milestone_activity.datetime_end IS NULL;";

        return getJdbcTemplate().query(sql, new AssignedMilestoneActivityMapper(), user.getUsername());
    }

    public AssignedMilestoneActivity getCurrentActivityByUser() {

        User user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return null;

        final String sql =
                "SELECT * " +
                        "       FROM assigned_milestone_activity " +
                        " INNER JOIN assigned_milestone " +
                        "         ON assigned_milestone.id = assigned_milestone_activity.assigned_milestone_id " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN task " +
                        "         ON task.id = assigned_milestone_activity.task_id " +
                        " INNER JOIN user " +
                        "         ON user.id = assigned_milestone.assigned_to " +
                        "      WHERE user.employee_number = ? " +
                        "        AND assigned_milestone_activity.datetime_end IS NULL" +
                        "      LIMIT 1;";

        try {
            return getJdbcTemplate().queryForObject(sql, new LastProjectActivityMapper(), user.getUsername());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public AssignedMilestone getAssignedMilestoneByActivityId(int milestoneActivityId) {

        User user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return null;

        final String sql =
                "SELECT * " +
                        "       FROM assigned_milestone " +
                        " INNER JOIN assigned_milestone_activity " +
                        "         ON assigned_milestone_activity.assigned_milestone_id = assigned_milestone.id " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        " INNER JOIN user assigned_to " +
                        "         ON assigned_to.id = assigned_milestone.assigned_to " +
                        " INNER JOIN assigned_milestone_status " +
                        "         ON assigned_milestone_status.id = assigned_milestone.assigned_milestone_status_id " +
                        " INNER JOIN user assigned_by " +
                        "         ON assigned_by.id = assigned_milestone.assigned_by " +
                        "      WHERE assigned_to.employee_number = ? " +
                        "        AND assigned_milestone_activity.id = ?;";

        try {
            return getJdbcTemplate().queryForObject(sql, new AssignedMilestoneMapper(), user.getUsername(), milestoneActivityId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
