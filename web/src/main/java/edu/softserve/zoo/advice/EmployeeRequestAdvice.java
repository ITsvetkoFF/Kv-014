package edu.softserve.zoo.advice;

import edu.softserve.zoo.controller.rest.EmployeeRestController;
import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.dto.RoleDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


/**
 * This class purpose is to intercept employee rest controller requests and modify request body:
 * convert employee role enum string representation to role dto object.
 *
 * @author Julia Siroshtan
 */
@ControllerAdvice(assignableTypes = EmployeeRestController.class)
public class EmployeeRequestAdvice implements RequestBodyAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRequestAdvice.class);
    private static final String ERROR_LOG_TEMPLATE = "An exception occurred during {} operation. Message: {}";

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
            throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        EmployeeDto dto = (EmployeeDto) body;
        Set<RoleDto> roles = new HashSet<>();
        dto.getRoles().stream().forEach(r -> roles.add(stringToRoleDto(r.toString())));
        dto.setRoles((Set) roles);
        return body;
    }

    private RoleDto stringToRoleDto(String role) {
        Role.Type type;
        try {
            type = Role.Type.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "convert string to role entity", e.getMessage());
            throw ApplicationException.getBuilderFor(ValidationException.class).forReason(ValidationException.Reason.ENUM_MAPPING_FAILED).build();
        }
        Long id = (long) type.ordinal();
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setType(type);
        return roleDto;
    }
}
