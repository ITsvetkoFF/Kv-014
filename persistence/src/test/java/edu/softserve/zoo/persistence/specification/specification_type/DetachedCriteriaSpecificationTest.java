package edu.softserve.zoo.persistence.specification.specification_type;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.stubs.detached_criteria.StubGetAllEmployeesDetachedCriteria;
import edu.softserve.zoo.persistence.specification.stubs.detached_criteria.StubGetEmployeeByIdDetachedCriteria;
import edu.softserve.zoo.persistence.specification.stubs.detached_criteria.StubGetEmployeesByFirstNameDetachedCriteria;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class DetachedCriteriaSpecificationTest extends AbstractSpecificationTypeTest {

    @Autowired
    EmployeeRepository repository;

    @Override
    public Collection<Employee> performFindAllQuery() {
        return repository.find(new StubGetAllEmployeesDetachedCriteria());
    }

    @Override
    public Collection<Employee> performFindByIdCorrectQuery() {
        return repository.find(new StubGetEmployeeByIdDetachedCriteria(CORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByIdIncorrectQuery() {
        return repository.find(new StubGetEmployeeByIdDetachedCriteria(INCORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByNameCorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameDetachedCriteria(CORRECT_FIRST_NAME));
    }

    @Override
    public Collection<Employee> performFindByNameIncorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameDetachedCriteria(INCORRECT_FIRST_NAME));
    }
}
