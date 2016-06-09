package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl extends AbstractService<Employee> implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    Repository<Employee> getRepository() {
        return employeeRepository;
    }

    @Override
    Class<Employee> getType() {
        return Employee.class;
    }
}
