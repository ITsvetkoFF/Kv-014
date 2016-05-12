package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.impl.EmployeeGetAllSpecification;
import edu.softserve.zoo.persistence.specification.impl.EmployeeGetByIdSpecification;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Julia Siroshtan
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<Employee> implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    @Override
    public Employee findOne(Integer id) {
        Collection<Employee> employees = employeeRepository.find(new EmployeeGetByIdSpecification(id));
        if (employees.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .build();
        }
        return employees.stream().findFirst().get();
    }

    @Transactional
    @Override
    public Collection<Employee> getAll() {
        return employeeRepository.find(new EmployeeGetAllSpecification());
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        employeeRepository.delete(findOne(id));
    }

    @Override
    Repository<Employee> getRepository() {
        return employeeRepository;
    }

}
