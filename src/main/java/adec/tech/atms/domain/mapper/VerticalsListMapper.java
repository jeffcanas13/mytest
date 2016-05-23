package adec.tech.atms.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class VerticalsListMapper implements RowMapper<Map<String, Object>>{
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException{
		VerticalMapper verticalMapper = new VerticalMapper();
		StatusMapper statusMapper = new StatusMapper();
		
		Map<String, Object> displayVerticals = new HashMap<>();
		displayVerticals.put("verticals", verticalMapper.mapRow(rs, i));
		displayVerticals.put("status", statusMapper.mapRow(rs, i));
	
		return displayVerticals;
	}
}
