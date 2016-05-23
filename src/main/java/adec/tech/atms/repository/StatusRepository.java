package adec.tech.atms.repository;

import adec.tech.atms.domain.Status;
import adec.tech.atms.domain.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/9/2016
 *         Time: 6:57 PM
 */

@Repository
public class StatusRepository extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Status getStatusByLabel(String label) {

        final String sql =
                "SELECT * " +
                        "       FROM status " +
                        "      WHERE label = ?;";

        try {

            return getJdbcTemplate().queryForObject(sql, new StatusMapper(), label);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
