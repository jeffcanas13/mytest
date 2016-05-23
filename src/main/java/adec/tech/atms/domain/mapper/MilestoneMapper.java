package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.Milestone;
import adec.tech.atms.domain.Project;
import adec.tech.atms.domain.Status;
import adec.tech.atms.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/10/2016
 *         Time: 3:05 PM
 */
public class MilestoneMapper implements RowMapper<Milestone> {
    @Override
    public Milestone mapRow(ResultSet rs, int i) throws SQLException {

        return new Milestone() {
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
                setStatus(new Status() {
                    {
                        setId(rs.getInt("status.id"));
                        setLabel(rs.getString("status.label"));
                    }
                });
                setCreatedBy(new User() {
                    {
                        setId(rs.getInt("created_by.id"));
                        setEmployeeNumber(rs.getString("created_by.employee_number"));
                        setFirstName(rs.getString("created_by.first_name"));
                        setMiddleName(rs.getString("created_by.middle_name"));
                        setLastName(rs.getString("created_by.last_name"));
                        setEmail(rs.getString("created_by.email"));
                        setLoginAttempts(rs.getInt("created_by.login_attempts"));
                        setCreatedDate(rs.getString("created_by.created_date"));
                    }
                });
                setCreatedDate(rs.getString("milestone.created_date"));
            }
        };
    }
}
