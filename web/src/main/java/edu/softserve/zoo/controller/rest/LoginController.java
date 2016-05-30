package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.LoginRequestDto;
import edu.softserve.zoo.dto.LoginResponseDto;
import edu.softserve.zoo.web.security.AuthTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.LOGIN;
import static edu.softserve.zoo.controller.rest.Routes.USER;

/**
 * Controller for login requests.
 * @author Ilya Doroshenko
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenUtils authTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Handles user login requests. Generates token for remember-me functionality.
     * @param loginRequestDto username and password
     * @return authentication remember-me token
     * @throws AuthenticationException if authentification fails
     */
    @RequestMapping(path = LOGIN, method = RequestMethod.POST)
    public LoginResponseDto loginRequest(@RequestBody LoginRequestDto loginRequestDto) throws AuthenticationException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername().toUpperCase(),
                        loginRequestDto.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String token = authTokenUtils.generateToken(userDetails);

        return new LoginResponseDto(token);
    }


}
