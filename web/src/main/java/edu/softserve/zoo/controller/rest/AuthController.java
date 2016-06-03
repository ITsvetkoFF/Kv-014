package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.LoginRequestDto;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.security.AuthUserDetails;
import edu.softserve.zoo.web.security.AuthTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static edu.softserve.zoo.controller.rest.Routes.LOGIN;
import static edu.softserve.zoo.controller.rest.Routes.LOGOUT;

/**
 * Controller for authentication requests handling.
 *
 * @author Ilya Doroshenko
 */
@RestController
public class AuthController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenUtils authTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmployeeService employeeService;

    @Value("${token.header}")
    private String headerName;

    /**
     * Handles user login request. Generates token for remember-me functionality.
     *
     * @param loginRequestDto username and password
     * @param response        response instance to set token header into
     * @throws AuthenticationException if authentication fails
     */
    @RequestMapping(path = LOGIN, method = RequestMethod.POST)
    public void loginRequest(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) throws AuthenticationException {

        /* Authenticating user */
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        /* Generating token */
        AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String token = authTokenUtils.generateToken(userDetails);

        /* Writing token ID to user record */
        Employee employee = employeeService.findOne(userDetails.getId(), Employee.class);
        employee.setToken(authTokenUtils.getIdFromToken(token));
        employeeService.update(employee);

        response.setHeader(headerName, token);

        LOGGER.info("User #{} received new auth token", userDetails.getId());
    }

    /**
     * Handles user logout request. Destroys remember-me token.
     */
    @RequestMapping(path = LOGOUT, method = RequestMethod.POST)
    public void logoutRequest() {

        /* Request of current user's info */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        /* Destroying current user's token binding */
        Employee employee = employeeService.findOne(userDetails.getId(), Employee.class);
        employee.setToken(null);
        employeeService.update(employee);

        LOGGER.info("User #{} signed out successfully", userDetails.getId());
    }
}
