package adec.tech.atms.web.rest;

import adec.tech.atms.domain.User;
import adec.tech.atms.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/13/2016
 *         Time: 6:06 PM
 */

@RestController
@RequestMapping("api")
public class AuthenticationResource {

    @RequestMapping(
            value = "account",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getAccount() {

        User user = SecurityUtil.getCurrentUserLogin();

        if (user == null)
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @RequestMapping(
            value = "login",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> doLogin(
            @RequestBody User user,
            HttpSession session
    ) throws SQLException {

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN", "USER", "ANONYMOUS");

        user.setAuthorities(authorities);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContext c = SecurityContextHolder.getContext();
        c.setAuthentication(auth);

        session.setMaxInactiveInterval(432000);

        User loggedInUser = SecurityUtil.getCurrentUserLogin();

        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);

    }

}
