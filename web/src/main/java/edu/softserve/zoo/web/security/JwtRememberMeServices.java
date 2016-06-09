package edu.softserve.zoo.web.security;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.security.AuthUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements JWT remember-me functionality.
 *
 * @author Ilya Doroshenko
 */
@Component
public class JwtRememberMeServices implements RememberMeServices, LogoutHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtRememberMeServices.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${token.header}")
    private String tokenHeaderName;

    /**
     * Called when security filter tries to authenticate request via its token.
     */
    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String authToken = request.getHeader(tokenHeaderName);
        String username = jwtUtils.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(authToken, userDetails)) {
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            } else {
                LOGGER.warn("Auth token filter encountered invalid token for user: {}", username);
            }
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * Called whenever an interactive authentication attempt is successful.
     * Generates auth token, places it to the response header.
     */
    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response,
                             Authentication successfulAuthentication) {

        AuthUserDetails userDetails = (AuthUserDetails) successfulAuthentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);

        /* Writing token ID to user record */
        Employee employee = employeeService.findOne(userDetails.getId(), Employee.class);
        employee.setToken(jwtUtils.getIdFromToken(token));
        employeeService.update(employee);

        response.setHeader(tokenHeaderName, token);

        LOGGER.info("User {} received new remember-me token", userDetails.getUsername());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authToken = request.getHeader(tokenHeaderName);
        String username = jwtUtils.getUsernameFromToken(authToken);

        if (username != null) {

            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(authToken, userDetails)) {
                /* Writing token ID to user record */
                Employee employee = employeeService.findOne(userDetails.getId(), Employee.class);
                employee.setToken(null);
                employeeService.update(employee);
            } else {
                LOGGER.warn("Auth token filter encountered invalid token for user: {}", username);
            }
        }
    }
}
