package edu.softserve.zoo.web.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ilya Doroshenko
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    private final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Error error = new Error();
        error.setMessage(exception.getMessage()+" it's from failure handler");

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
