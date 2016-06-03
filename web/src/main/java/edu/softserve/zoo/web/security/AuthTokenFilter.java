package edu.softserve.zoo.web.security;

import edu.softserve.zoo.service.security.AuthUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Filter which intercepts all request for routes that require authentication.
 * Checks the validity of the remember-me token and authenticate user for current request.
 *
 * @author Ilya Doroshenko
 */
@Component
public class AuthTokenFilter extends UsernamePasswordAuthenticationFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthTokenUtils tokenUtils;

    @Value("${token.header}")
    private String tokenHeader;

    /**
     * Intercepts requests which require authentication.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(tokenHeader);
        String username = tokenUtils.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(username);

            if (tokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                /* Setting currently authenticated user */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                LOGGER.info("User #{} is authenticated through the token", userDetails.getId());
                LOGGER.debug(" -- His authorities are: {}", userDetails.getAuthorities());
            } else {

                LOGGER.warn("Auth token filter encountered invalid token for user: {}", username);
            }
        }
        chain.doFilter(request, response);
    }
}
