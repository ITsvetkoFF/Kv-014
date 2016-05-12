package edu.softserve.zoo.persistence.specification.specification_type;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.stubs.hql.StubGetAllEmployeesHQL;
import edu.softserve.zoo.persistence.specification.stubs.hql.StubGetEmployeeByIdHQL;
import edu.softserve.zoo.persistence.specification.stubs.hql.StubGetEmployeesByFirstNameHQL;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/persistence-test-ctx.xml")
public class HQLSpecificationTest extends AbstractSpecificationTypeTest {

    @Autowired
    EmployeeRepository repository;

    @Override
    public Collection<Employee> performFindAllQuery() {
        return repository.find(new StubGetAllEmployeesHQL());
    }

    @Override
    public Collection<Employee> performFindByIdCorrectQuery() {
        return repository.find(new StubGetEmployeeByIdHQL(CORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByIdIncorrectQuery() {
        return repository.find(new StubGetEmployeeByIdHQL(INCORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByNameCorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameHQL(CORRECT_FIRST_NAME));
    }

    @Override
    public Collection<Employee> performFindByNameIncorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameHQL(INCORRECT_FIRST_NAME));
    }
}
