package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.USER;

/**
 * Controller for operations with currently authentificated user.
 *
 * @author Ilya Doroshenko
 */
@RestController
@RequestMapping(USER)
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Handles the request for info about currently authenticated user
     * @return current user
     */
    @RequestMapping(method = RequestMethod.GET)
    public Employee getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        Employee employee = employeeService.getEmployeeByEmail(email);

        /*todo: employeeDTO*/
        return employee;
    }
}