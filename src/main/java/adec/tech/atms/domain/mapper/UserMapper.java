package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.Status;
import adec.tech.atms.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/9/2016
 *         Time: 9:51 AM
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        return new User() {
            {
                setId(rs.getInt("user.id"));
                setEmployeeNumber(rs.getString("user.employee_number"));
                setFirstName(rs.getString("user.first_name"));
                setMiddleName(rs.getString("user.middle_name"));
                setLastName(rs.getString("user.last_name"));
                setEmail(rs.getString("user.email"));
                //setPassword(rs.getString("user.password"));
                setLoginAttempts(rs.getInt("user.login_attempts"));
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
                        //setPassword(rs.getString("created_by.password"));
                        setLoginAttempts(rs.getInt("created_by.login_attempts"));
                        setCreatedDate(rs.getString("created_by.created_date"));
                    }
                });
                setCreatedDate(rs.getString("user.created_date"));
            }
        };
    }
}
