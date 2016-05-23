package adec.tech.atms.repository;

import adec.tech.atms.domain.AssignedMilestone;
import adec.tech.atms.domain.Milestone;
import adec.tech.atms.domain.Status;
import adec.tech.atms.domain.User;
import adec.tech.atms.domain.mapper.MilestoneMapper;
import adec.tech.atms.domain.mapper.ProjectAssignedMilestoneMapper;
import adec.tech.atms.repository.util.TableQuery;
import adec.tech.atms.security.SecurityUtil;
import adec.tech.atms.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/10/2016
 *         Time: 2:51 PM
 */

@Repository
public class MilestoneRepository extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TableQuery tablequery;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private Pagination pagination;

    public Map<String, Object> getAssignedMilestones(String searchQuery, Integer pageNumber) {

        int offset = pagination.getOffset(pageNumber, 5);

        UserDetails user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return null;

        final String query =
                "SELECT *       " +
                        "       FROM assigned_milestone " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        "        AND milestone.status_id = 1 " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        "        AND project.status_id = 1 " +
                        " INNER JOIN user assigned_to " +
                        "         ON assigned_to.id = assigned_milestone.assigned_to " +
                        " INNER JOIN user assigned_by " +
                        "         ON assigned_by.id = assigned_milestone.assigned_by " +
                        " INNER JOIN assigned_milestone_status " +
                        "         ON assigned_milestone_status.id = assigned_milestone.assigned_milestone_status_id " +
                        "      WHERE assigned_milestone.assigned_milestone_status_id = 1 " +
                        "        AND assigned_to.employee_number = ? " +
                        "        AND CONCAT_WS('', project.name, milestone.title) " +
                        "       LIKE ? " +
                        "   ORDER BY assigned_milestone.assigned_date " +
                        "      LIMIT ?, 5;";

        int totalRows = tablequery.getTotalRows(
                "              assigned_milestone " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        "        AND milestone.status_id = 1 " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        "        AND project.status_id = 1 " +
                        " INNER JOIN user assigned_to " +
                        "         ON assigned_to.id = assigned_milestone.assigned_to " +
                        " INNER JOIN user assigned_by " +
                        "         ON assigned_by.id = assigned_milestone.assigned_by " +
                        " INNER JOIN assigned_milestone_status " +
                        "         ON assigned_milestone_status.id = assigned_milestone.assigned_milestone_status_id " +
                        "      WHERE assigned_milestone.assigned_milestone_status_id = 1 " +
                        "        AND assigned_to.employee_number = ? " +
                        "        AND CONCAT_WS('', project.name, milestone.title) " +
                        "       LIKE ?;"

                , new Object[]{user.getUsername(), "%" + searchQuery + "%"}).intValue();

        Map<String, Object> projectAssignedMilestonesMap = new HashMap<>();

        List<AssignedMilestone> projectAssignedMilestone = getJdbcTemplate().query(query, new ProjectAssignedMilestoneMapper(), user.getUsername(), "%" + searchQuery + "%", offset);
        projectAssignedMilestonesMap.put("AssignedMilestones", projectAssignedMilestone);
        projectAssignedMilestonesMap.put("TotalRows", totalRows);

        return projectAssignedMilestonesMap;
    }

    public boolean isAssignedToUser(int id) {

        UserDetails user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return false;

        final String sql =
                "SELECT count(*) " +
                        "           FROM assigned_milestone " +
                        "     INNER JOIN milestone " +
                        "             ON milestone.id = assigned_milestone.milestone_id " +
                        "            AND milestone.status_id = 1 " +
                        "     INNER JOIN user " +
                        "             ON user.id = assigned_milestone.assigned_to " +
                        "            AND user.status_id = 1 " +
                        "          WHERE assigned_milestone.id = ?" +
                        "            AND assigned_milestone.assigned_milestone_status_id = 1 " +
                        "            AND user.employee_number = ?;";

        Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, id, user.getUsername());

        return count > 0;
    }

    public Map<String, Object> getProjectMilestones(int projectId, String searchQuery, int pageNumber) {

        int offset = pagination.getOffset(pageNumber, 20);

        final String sql =
                "SELECT * " +
                        "       FROM milestone " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN status " +
                        "         ON status.id = milestone.status_id " +
                        " INNER JOIN user created_by " +
                        "         ON created_by.id = milestone.created_by " +
                        "      WHERE milestone.project_id = ? " +
                        "        AND CONCAT_WS('', milestone.title, milestone.description, CONCAT_WS(', ', created_by.last_name, created_by.first_name)) " +
                        "       LIKE ? " +
                        "   ORDER BY milestone.title " +
                        "      LIMIT ?, 20;";

        int totalRows = tablequery.getTotalRows(
                "               milestone " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN status " +
                        "         ON status.id = milestone.status_id " +
                        " INNER JOIN user created_by " +
                        "         ON created_by.id = milestone.created_by " +
                        "      WHERE milestone.project_id = ? " +
                        "        AND CONCAT_WS('', milestone.title, milestone.description, CONCAT_WS(', ', created_by.last_name, created_by.first_name)) " +
                        "       LIKE ?;"
                , new Object[]{projectId, "%" + searchQuery + "%"}).intValue();

        Map<String, Object> projectMilestoneMap = new HashMap<>();

        List<Milestone> milestones = getJdbcTemplate().query(sql, new MilestoneMapper(), projectId, "%" + searchQuery + "%", offset);
        projectMilestoneMap.put("Milestones", milestones);
        projectMilestoneMap.put("TotalRows", totalRows);

        return projectMilestoneMap;
    }

    public Milestone getMilestoneById(int milestoneId) {

        final String sql =
                "SELECT * " +
                        "       FROM milestone " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN status " +
                        "         ON status.id = milestone.status_id " +
                        " INNER JOIN user created_by " +
                        "         ON created_by.id = milestone.created_by " +
                        "      WHERE milestone.id = ?;";

        try {
            return getJdbcTemplate().queryForObject(sql, new MilestoneMapper(), milestoneId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int createMilestone(Milestone milestone, int projectId) {

        User user = SecurityUtil.getCurrentUserLogin();

        final String sql =
                "INSERT INTO " +
                        "       milestone (title," +
                        "                  description, " +
                        "                  target_hours, " +
                        "                  project_id, " +
                        "                  status_id, " +
                        "                  created_by, " +
                        "                  created_date " +
                        "                  ) " +
                        "       VALUES(?, ?, ?, ?, 1, ?, NOW()); ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, milestone.getTitle());
            ps.setString(2, milestone.getDescription());
            ps.setDouble(3, milestone.getTargetHours());
            ps.setInt(4, projectId);
            ps.setInt(5, user.getId());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void updateMilestone(Milestone milestone) {

        final String sql =
                "UPDATE milestone " +
                        "       SET title = ?, " +
                        "           description = ?, " +
                        "           target_hours = ? " +
                        "     WHERE id = ?;";

        getJdbcTemplate().update(sql, milestone.getTitle(), milestone.getDescription(), milestone.getTargetHours(), milestone.getId());
    }

    public void setMilestoneStatus(int milestoneId, Status status) {

        final String sql =
                "UPDATE milestone " +
                        "       SET status_id = ? " +
                        "     WHERE id = ?;";

        getJdbcTemplate().update(sql, status.getId(), milestoneId);
    }

}
