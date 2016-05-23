package adec.tech.atms.repository.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author Jonathan Leijendekker
 *         Date: 02/20/2016
 *         Time: 11:53 AM
 */

@Repository
public class TableQuery extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Number getTotalRows(String tableQuery, Object[] filters) {

        final String sql = String.format(
                "SELECT COUNT(1) " +
                        "       FROM %s;"
        , tableQuery);

        return getJdbcTemplate().queryForObject(sql, filters, Number.class);
    }

}
