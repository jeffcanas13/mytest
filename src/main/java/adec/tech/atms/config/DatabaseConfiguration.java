package adec.tech.atms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/14/2016
 *         Time: 8:05 PM
 */

@Configuration
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) throws SQLException, IOException {

        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                            " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        log.info("Configuring the datasource");

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        String connectionProperties = "";
        if (env.getProperty("spring.datasource.connection-properties") != null)
            connectionProperties = env.getProperty("spring.datasource.connection-properties");

        driverManagerDataSource.setUrl(dataSourceProperties.getUrl() + dataSourceProperties.getName() + connectionProperties);
        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        driverManagerDataSource.setUsername(dataSourceProperties.getUsername());
        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());

        log.info("Datasource is now configured");

        log.info("Checking if the '{}' database exists...", dataSourceProperties.getName());

        Connection conn = null;

        try {
            conn = driverManagerDataSource.getConnection();

            log.info("Database '{}' already exists", dataSourceProperties.getName());
        } catch (SQLException e) {

            if (dataSourceProperties.getName() == null || dataSourceProperties.getName().trim().length() == 0)
                throw new SQLException("Database name is empty");

            if (("Unknown database '" + dataSourceProperties.getName() + "'").equals(e.getMessage())) {

                log.info("Database '{}' is missing, creating database", dataSourceProperties.getName());

                driverManagerDataSource.setUrl(dataSourceProperties.getUrl());

                try {
                    conn = driverManagerDataSource.getConnection();

                    Statement s = conn.createStatement();
                    s.executeUpdate("CREATE SCHEMA " + dataSourceProperties.getName() + ";");

                    s.close();

                    log.info("Reconnecting to the database");

                    driverManagerDataSource = new DriverManagerDataSource();

                    driverManagerDataSource.setUrl(dataSourceProperties.getUrl() + dataSourceProperties.getName() + connectionProperties);
                    driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
                    driverManagerDataSource.setUsername(dataSourceProperties.getUsername());
                    driverManagerDataSource.setPassword(dataSourceProperties.getPassword());

                    log.info("Database '{}' was successfully created", dataSourceProperties.getName());

                } catch (SQLException e1) {
                    throw new SQLException(e1.getMessage());
                }

            } else {
                log.error("Error creating the database: {}", e);
            }

        } finally {
            if (conn != null && !conn.isClosed())
                conn.close();
        }

        return driverManagerDataSource;

    }

}
