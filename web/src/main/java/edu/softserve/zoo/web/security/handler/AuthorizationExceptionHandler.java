package edu.softserve.zoo.web.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ilya Doroshenko
 */
@Component
public class AuthorizationExceptionHandler implements AccessDeniedHandler {

    private final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Error error = new Error();
        error.setMessage(accessDeniedException.getMessage());

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
