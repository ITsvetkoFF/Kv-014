package edu.softserve.zoo.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
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

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * Unified exception handler for all security exceptions types.
 *
 * @author Ilya Doroshenko
 */
@Component
public class ErrorHandler implements AuthenticationFailureHandler, AuthenticationEntryPoint, AccessDeniedHandler {

    private final String JSON_CONTENT_TYPE = "application/json";
    private final String AUTHENTICATION_REQUIRED = "Security.authentication.required";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Is called when unauthenticated user tries to access resources which require authentication.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String message = messageSource.getMessage(AUTHENTICATION_REQUIRED, null,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), getLocale());

        writeErrorToResponse(message, response);
    }

    /**
     * Is called when user attempts to access protected resource without any required authorities.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        writeErrorToResponse(exception.getMessage(), response);

    }

    /**
     * Called on exception encountering during authentication process.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        writeErrorToResponse(exception.getMessage(), response);
    }

    private void writeErrorToResponse(String message, HttpServletResponse response) throws IOException {
        response.setContentType(JSON_CONTENT_TYPE);

        Error error = new Error(message);

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
