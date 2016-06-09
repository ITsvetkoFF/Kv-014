package edu.softserve.zoo.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter which intercepts unauthenticated requests for secured resources.
 * Uses {@link RememberMeServices} that authenticates request if there is a valid authentication token in it.
 *
 * @author Ilya Doroshenko
 */
@Component
public class TokenAuthFilter extends GenericFilterBean {

    @Autowired
    RememberMeServices rememberMeServices;

    /**
     * Intercepts request, attempts to authenticate it.
     *
     * @param request  to pass to {@link RememberMeServices}
     * @param response to pass to {@link RememberMeServices}
     * @param chain    to continue
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication rememberMeAuth = rememberMeServices.autoLogin(req, res);

            if (rememberMeAuth != null) {
                SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);
            }
        }
        chain.doFilter(request, response);
    }
}
