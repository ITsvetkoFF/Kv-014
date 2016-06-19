package edu.softserve.zoo.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unified exception handler for all security exceptions types.
 *
 * @author Ilya Doroshenko
 */
@Component
public class ErrorHandler implements AuthenticationFailureHandler, AuthenticationEntryPoint, AccessDeniedHandler {

    private final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Is called when unauthenticated user tries to access resources which require authentication.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        handleError(exception, response);
    }

    /**
     * Is called when user attempts to access protected resource without any required authorities.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        handleError(exception, response);

    }

    /**
     * Called on exception encountering during authentication process.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        handleError(exception, response);
    }

    private void handleError(Exception exception, HttpServletResponse response) throws IOException {
        response.setContentType(JSON_CONTENT_TYPE);

        Error error = new Error(exception.getMessage());

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
