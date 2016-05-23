package adec.tech.atms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/29/2016
 *         Time: 10:57 AM
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * AngularJS routes
     */
    @Controller
    static class AngularRoutes {

        @RequestMapping({
                "/",
                "login",
                "verticals/**",
                "projects/**"
        })
        public String index() {
            return "index";
        }

    }

}
