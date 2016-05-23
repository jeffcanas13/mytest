package adec.tech.atms.repository;

import adec.tech.atms.domain.Task;
import adec.tech.atms.domain.mapper.TaskMapper;
import adec.tech.atms.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/12/2016
 *         Time: 4:00 PM
 */

@Repository
public class TaskRepository extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public boolean isTaskExisting(int id) {

        final String sql =
                "SELECT count(*) " +
                        "       FROM task " +
                        "      WHERE id = ? " +
                        "        AND status_id = 1; ";


        Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, id);

        return count > 0;

    }

    public boolean isTaskAssignedToUser(int id) {

        UserDetails user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return false;

        final String sql =
                "SELECT count(*) " +
                        "         FROM task " +
                        "   INNER JOIN taskset_task " +
                        "           ON taskset_task.task_id = task.id " +
                        "          AND taskset_task.status_id = 1 " +
                        "   INNER JOIN user_taskset " +
                        "           ON user_taskset.taskset_id = taskset_task.taskset_id " +
                        "   INNER JOIN user " +
                        "           ON user.id = user_taskset.user_id " +
                        "          AND user.status_id = 1 " +
                        "        WHERE user.employee_number = ? " +
                        "          AND task.id = ?;";

        Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, user.getUsername(), id);

        return count > 0;

    }

    public List<Task> getUserTasks() {

        UserDetails user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return null;

        final String sql =
                "SELECT * " +
                        "       FROM taskset_task " +
                        " INNER JOIN task " +
                        "         ON task.id = taskset_task.task_id " +
                        "        AND task.status_id = 1 " +
                        " INNER JOIN taskset " +
                        "         ON taskset.id = taskset_task.taskset_id " +
                        "        AND taskset.status_id = 1 " +
                        " INNER JOIN user_taskset " +
                        "         ON user_taskset.taskset_id = taskset.id " +
                        " INNER JOIN user " +
                        "         ON user.id = user_taskset.user_id " +
                        " INNER JOIN user created_by " +
                        "         ON created_by.id = task.created_by " +
                        " INNER JOIN status " +
                        "         ON status.id = taskset_task.status_id " +
                        "      WHERE user.employee_number = ? " +
                        "   ORDER BY task.name;";

        return getJdbcTemplate().query(sql, new TaskMapper(), user.getUsername());

    }

}
