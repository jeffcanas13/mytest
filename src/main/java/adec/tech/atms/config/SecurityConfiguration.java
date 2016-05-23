package adec.tech.atms.config;

import adec.tech.atms.security.AjaxLogoutSuccessHandler;
import adec.tech.atms.security.Http401UnauthorizedEntryPoint;
import adec.tech.atms.web.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/13/2016
 *         Time: 12:18 PM
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Autowired
    private Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/bower_components/**")
                .antMatchers("/dist/**")
                .antMatchers("/images/**")
                .antMatchers("/scripts/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
            .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(http401UnauthorizedEntryPoint)
            .and()
                .addFilterBefore(new Filter(), BasicAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()

        ;
    }

}
