package adec.tech.atms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/13/2016
 *         Time: 12:18 PM
 */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AtmsApplication {

    private static final Logger log = LoggerFactory.getLogger(AtmsApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication atms = new SpringApplication(AtmsApplication.class);
        atms.setBannerMode(Banner.Mode.CONSOLE);
        Environment environment = atms.run(args).getEnvironment();
        log.info("Access URLs:"
                        + "\n"
                        + "----------------------------------------------------------"
                        + "\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}"
                        + "\n\t"
                        + "External: \thttp://{}:{}"
                        + "\n"
                        + "----------------------------------------------------------",
                environment.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }
}
