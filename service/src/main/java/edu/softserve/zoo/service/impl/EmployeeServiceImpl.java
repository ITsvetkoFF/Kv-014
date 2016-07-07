package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.employee.GetAllEmployeesWithRolesSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.employee.GetEmployeeByEmailSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.employee.GetEmployeeByIdWithRolesSpecification;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.notifier.EmployeeNotifier;
import edu.softserve.zoo.service.security.PasswordGenerator;
import edu.softserve.zoo.service.util.BeanPropertiesUtils;
import edu.softserve.zoo.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link EmployeeService}.
 *
 * @author Ilya Doroshenko
 */
@Service
public class EmployeeServiceImpl extends AbstractService<Employee> implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeNotifier employeeNotifier;

    @Value("${password.length}")
    private int passwordLength;

    @Override
    Repository<Employee> getRepository() {
        return employeeRepository;
    }

    /**
     * Returns employee with specified ID. Employee's roles set is loaded.
     *
     * @param id identifier of required entity
     * @return found employee
     */
    @Override
    @Transactional
    public Employee findOne(Long id) {
        Employee result = employeeRepository.findOne(new GetEmployeeByIdWithRolesSpecification(id));
        Validator.notNull(result, ApplicationException.getBuilderFor(NotFoundException.class)
                .forReason(NotFoundException.Reason.BY_ID).build());
        return result;
    }

    /**
     * Returns all employees with role sets loaded.
     *
     * @return all employees
     */
    @Override
    @Transactional
    public List<Employee> findAll() {
        return employeeRepository.find(new GetAllEmployeesWithRolesSpecification());
    }

    /**
     * Saves employee to repository.
     * Before save:
     * <ul>
     * <li>Uppercases its email address</li>
     * <li>Generates password of specified length</li>
     * </ul>
     * Then sends a mail with generated password to employee's email address.
     *
     * @param entity employee to save
     * @return saved employee
     */
    @Override
    @Transactional
    public Employee save(Employee entity) {
        String password = PasswordGenerator.generate(passwordLength);
        String encodedPassword = passwordEncoder.encode(password);
        entity.setPassword(encodedPassword);
        entity.setPasswordChangeDate(LocalDateTime.now());

        entity.setEmail(StringUtils.upperCase(entity.getEmail()));

        Employee savedEmployee = super.save(entity);

        employeeNotifier.sendPassword(savedEmployee, password);

        return savedEmployee;
    }

    /**
     * Updates specified entity in repository.
     *
     * @param entity to update
     * @return updated entity
     */
    @Override
    @Transactional
    public Employee update(Employee entity) {
        Employee employee = findOne(entity.getId());
        BeanPropertiesUtils.copyPropertiesIgnoringNulls(entity, employee);
        return super.update(employee);
    }

    /**
     * Loads employee from repository by specified email.
     *
     * @param email of employee to load
     * @return loaded employee
     */
    @Override
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findOne(new GetEmployeeByEmailSpecification(email));

        Validator.notNull(employee, ApplicationException.getBuilderFor(NotFoundException.class)
                .forReason(NotFoundException.Reason.BY_EMAIL).build());
        return employee;
    }

    @Override
    Class<Employee> getType() {
        return Employee.class;
    }
}
