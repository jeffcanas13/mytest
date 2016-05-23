package adec.tech.atms.domain.mapper;

import adec.tech.atms.domain.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/9/2016
 *         Time: 6:59 PM
 */
public class StatusMapper implements RowMapper<Status> {
    @Override
    public Status mapRow(ResultSet rs, int i) throws SQLException {
        return new Status() {
            {
                setId(rs.getInt("status.id"));
                setLabel(rs.getString("status.label"));
            }
        };
    }
}
