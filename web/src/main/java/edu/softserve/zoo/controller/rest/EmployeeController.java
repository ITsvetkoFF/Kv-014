package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.EMPLOYEE;

/**
 * @author Ilya Doroshenko
 */
@RestController
@RequestMapping(EMPLOYEE)
public class EmployeeController extends AbstractRestController<EmployeeDto, Employee> {

    @Autowired
    private EmployeeService employeeService;

    protected EmployeeController() {
        super(Employee.class, EmployeeDto.class);
    }

    @Override
    protected Service<Employee> getService() {
        return employeeService;
    }
}
