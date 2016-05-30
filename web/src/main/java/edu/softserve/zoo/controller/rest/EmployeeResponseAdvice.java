package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.controller.rest.EmployeeController;
import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.dto.RoleDto;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class purpose is to intercept employee rest controller responses and modify response body:
 * convert employee role dto object to role enum string representation.
 *
 * @author Julia Siroshtan
 */
@ControllerAdvice(assignableTypes = EmployeeController.class)
public class EmployeeResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Stream employees;
        if (List.class.isAssignableFrom(body.getClass())) {
            employees = ((List) body).stream();
        } else {
            employees = Stream.of(body);
        }
        employees.forEach(o -> {
            EmployeeDto dto = (EmployeeDto) o;
            Set<String> roles = dto.getRoles().stream().map(role -> ((RoleDto) role).getType().toString())
                    .collect(Collectors.toSet());
            dto.setRoles((Set) roles);
        });
        return body;
    }
}
