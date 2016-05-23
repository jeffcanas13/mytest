package adec.tech.atms.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.PreparedStatement;

import adec.tech.atms.config.Constants;
import adec.tech.atms.domain.User;
import adec.tech.atms.domain.Vertical;
import adec.tech.atms.domain.mapper.VerticalsListMapper;
import adec.tech.atms.security.SecurityUtil;

@Repository
public class VerticalRepository extends JdbcDaoSupport {

	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	public int saveVertical(Vertical vertical)
	{
		User user = SecurityUtil.getCurrentUserLogin();
		
		if (user == null)
            return 0;
		
		// TODO, get user id after login
		user.setId(1);
		
		final String sql = "INSERT INTO" +
								" vertical" +
								" (" + 
								" name, " +
								" code, " +
								" description," + 
								" status_id, " +
								" created_by, " +
								" created_date" +
								" ) " + 
								"VALUES(?, ?, ?, '1', ?, NOW());";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			 getJdbcTemplate().update(
		                connection -> {
		                    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, new String[]{"id"} );
		                    ps.setString(1, vertical.getName());
		                    ps.setString(2, vertical.getCode());
		                    ps.setString(3, vertical.getDescription());
		                    ps.setInt(4, user.getId());
		                    
		                    return ps;
		                }, keyHolder
		        );
	
		        return keyHolder.getKey().intValue();
		}
	
	
	 public boolean isVerticalExistingAndActive(int verticalid) {

	        final String sql =
	                "SELECT count(*) " +
	                        "       FROM vertical " +
	                        "      WHERE id = ? " +
	                        "        AND status_id = 1; ";


	        Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, verticalid);

	        return count > 0;

	}	

	public List<Map<String, Object>> getVerticalsList() {

		final String sql = 
				"SELECT * " + 
							"FROM vertical " +
						"INNER JOIN status " +
								" ON vertical.status_id = status.id;";
		
		List<Map<String, Object>> displayVerticals = getJdbcTemplate().query(sql, new VerticalsListMapper());
		return displayVerticals; 
	}
	

	public String setStatus(int verticalId, int statusId) {
		
		//TODO - Add audit trail

		final String sql = "UPDATE vertical " 
		             + "       SET status_id = ? " 
				     + "     WHERE id = ?;";

		getJdbcTemplate().update(sql, statusId, verticalId);

		return Constants.SUCCESS;
	}

	public boolean isVerticalExisting(int verticalId) {
		final String sql = "SELECT count(*) "
				+ "           FROM vertical "
				+ "          WHERE id = ?;";

		Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, verticalId);

		return count > 0;
	}
	
	public Integer getStatus(int verticalId) {
		final String sql = "SELECT status_id "
				+ "           FROM vertical "
				+ "          WHERE id = ?;";

		return getJdbcTemplate().queryForObject(sql, Integer.class, verticalId);
	}
}
