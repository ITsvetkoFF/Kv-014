package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.impl.employee.GetEmployeeByEmailSpecification;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.exception.ServiceReason;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * @author Ilya Doroshenko
 */
@Service
public class EmployeeServiceImpl extends AbstractService<Employee> implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    Repository<Employee> getRepository() {
        return employeeRepository;
    }

    @Override
    @Transactional
    public Employee save(Employee entity) {
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        String upperCaseEmail = entity.getEmail().toUpperCase(Locale.ROOT);
        entity.setPassword(encodedPassword);
        entity.setEmail(upperCaseEmail);
        return super.save(entity);
    }

    @Override
    @Transactional
    public Employee update(Employee entity) {
        String upperCaseEmail = entity.getEmail().toUpperCase(Locale.ROOT);
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
        entity.setEmail(upperCaseEmail);
        return super.update(entity);
    }

    @Override
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        List<Employee> employees = employeeRepository.find(new GetEmployeeByEmailSpecification(email));

        Validator.notEmpty(employees, ApplicationException.getBuilderFor(NotFoundException.class)
                .forReason(ServiceReason.NOT_FOUND).build());

        Employee employee = employees.stream().findFirst().get();
        return employee;
    }

    @Override
    Class<Employee> getType() {
        return Employee.class;
    }
}
