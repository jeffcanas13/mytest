package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.Status;
import adec.tech.atms.domain.Task;
import adec.tech.atms.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/12/2016
 *         Time: 10:37 AM
 */
public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {

        return new Task() {
            {
                setId(rs.getInt("task.id"));
                setCode(rs.getString("task.code"));
                setName(rs.getString("task.name"));
                setDescription(rs.getString("task.description"));
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
                setCreatedDate(rs.getString("task.created_date"));
            }
        };

    }
}
