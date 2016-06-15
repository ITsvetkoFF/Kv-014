package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.Employee;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link EmployeeRepository} specific for {@link Employee} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class EmployeeRepositoryImpl extends AbstractRepository<Employee> implements EmployeeRepository {
}
