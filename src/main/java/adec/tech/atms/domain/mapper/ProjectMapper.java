package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/26/2016
 *         Time: 11:30 AM
 */
public class ProjectMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int i) throws SQLException {
        return new Project() {
            {
                setId(rs.getInt("project.id"));
                setCode(rs.getString("project.code"));
                setName(rs.getString("project.name"));
                setDescription(rs.getString("project.description"));
                setDateStart(rs.getString("project.date_start"));
                setDateEnd(rs.getString("project.date_end"));
                setTargetHours(rs.getDouble("project.target_hours"));
                setSourceMilestone(new Milestone() {
                    {
                        setId(rs.getInt("source_milestone.id"));
                        setTitle(rs.getString("source_milestone.title"));
                        setDescription(rs.getString("source_milestone.description"));
                        setTargetHours(rs.getDouble("source_milestone.target_hours"));
                        setCreatedDate(rs.getString("source_milestone.created_date"));
                    }
                });
                setOwnerGroup(new Group() {
                    {
                        setId(rs.getInt("owner_group.id"));
                        setName(rs.getString("owner_group.name"));
                        setDescription(rs.getString("owner_group.description"));
                        setCreatedDate(rs.getString("owner_group.created_date"));
                    }
                });
                setVertical(new Vertical(){
                	{
                	setId(rs.getInt("vertical.id"));
                	setName(rs.getString("vertical.name"));
                	setCode(rs.getString("vertical.code"));
                	setDescription(rs.getString("vertical.description"));
                	setCreatedDate(rs.getString("vertical.created_date"));
                	}
                });
                setClient(rs.getString("project.client"));
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
                setCreateDate(rs.getString("project.create_date"));
            }
        };
    }
}
