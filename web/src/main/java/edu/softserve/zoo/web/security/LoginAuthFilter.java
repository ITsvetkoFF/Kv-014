package edu.softserve.zoo.web.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
        return StringUtils.upperCase(request.getHeader(usernameHeader));
    }
}
