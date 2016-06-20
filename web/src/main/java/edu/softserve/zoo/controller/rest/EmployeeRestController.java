package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.EMPLOYEES;

/**
 * Employee REST controller
 *
 * @author Julia Siroshtan
 */
@RestController
@RequestMapping(EMPLOYEES)
public class EmployeeRestController extends AbstractRestController<EmployeeDto, Employee> {

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected Service<Employee> getService() {
        return employeeService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<EmployeeDto> getAll() {
        return super.getAll();
    }
}
