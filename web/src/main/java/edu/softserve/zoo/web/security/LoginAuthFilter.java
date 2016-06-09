package edu.softserve.zoo.web.security;

import edu.softserve.zoo.controller.rest.Routes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter which intercepts login requests.
 * <p>Looks for username and password in request headers (headers' names to look for are supplied via system properties).
 * Delegates request to {@link org.springframework.security.web.authentication.RememberMeServices} for returning token
 * to user in case of successful authentication. </p>
 *
 * @author Ilya Doroshenko
 */
@Component
public class LoginAuthFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${login.header.username}")
    private String usernameHeader;

    @Value("${login.header.password}")
    private String passwordHeader;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getHeader(passwordHeader);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getHeader(usernameHeader);
    }
}
