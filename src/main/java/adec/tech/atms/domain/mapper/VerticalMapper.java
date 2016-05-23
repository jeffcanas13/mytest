package adec.tech.atms.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import adec.tech.atms.domain.Vertical;

public class VerticalMapper implements RowMapper<Vertical>{

	@Override
	public Vertical mapRow(ResultSet rs, int i) throws SQLException{
		return new Vertical() {
			{
			setId(rs.getInt("vertical.id"));
			setName(rs.getString("vertical.name"));
			setCode(rs.getString("vertical.code"));
			setDescription(rs.getString("vertical.description"));
			setStatusId(rs.getInt("vertical.status_id"));
			setCreatedBy(rs.getInt("vertical.created_by"));
			setCreatedDate(rs.getString("created_date"));
			}


		};
	}
}
