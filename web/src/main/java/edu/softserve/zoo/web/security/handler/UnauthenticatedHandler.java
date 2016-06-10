package edu.softserve.zoo.web.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler for all unauthorized request to secured endpoints.
 *
 * @author Ilya Doroshenko
 */
@Component
public class UnauthenticatedHandler implements AuthenticationEntryPoint {

    private final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType(JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Error error = new Error();
        error.setMessage(e.getMessage());

        objectMapper.writeValue(response.getOutputStream(), error);
    }

}
