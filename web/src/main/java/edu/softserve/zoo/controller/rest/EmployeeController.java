package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.EmployeeController.EMPLOYEE;
import static edu.softserve.zoo.controller.rest.AbstractRestController.API_V1;

/**
 * @author Julia Siroshtan
 */
@RestController
@RequestMapping(API_V1 + EMPLOYEE)
public class EmployeeController extends AbstractRestController<EmployeeDto, Employee> {

    static final String EMPLOYEE = "/employee";

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {
        super(Employee.class, EmployeeDto.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<EmployeeDto> getAll() {
        Collection<Employee> employees = employeeService.getAll();
        return employees.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto getOne(@PathVariable Integer id) {
        return convertToDto(employeeService.findOne(id));
    }

    @Override
    protected Service<Employee> getService() {
        return employeeService;
    }
}
