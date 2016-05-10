package edu.softserve.zoo.persistence.specification.specification_type;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.stubs.sql.StubGetAllEmployeesSQL;
import edu.softserve.zoo.persistence.specification.stubs.sql.StubGetEmployeeByIdSQL;
import edu.softserve.zoo.persistence.specification.stubs.sql.StubGetEmployeesByFirstNameSQL;
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
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class SQLSpecificationTest extends AbstractSpecificationTypeTest {

    @Autowired
    EmployeeRepository repository;

    @Override
    public Collection<Employee> performFindAllQuery() {
        return repository.find(new StubGetAllEmployeesSQL());
    }

    @Override
    public Collection<Employee> performFindByIdCorrectQuery() {
        return repository.find(new StubGetEmployeeByIdSQL(CORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByIdIncorrectQuery() {
        return repository.find(new StubGetEmployeeByIdSQL(INCORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByNameCorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameSQL(CORRECT_FIRST_NAME));
    }

    @Override
    public Collection<Employee> performFindByNameIncorrectQuery() {
        return repository.find(new StubGetEmployeesByFirstNameSQL(INCORRECT_FIRST_NAME));
    }
}
