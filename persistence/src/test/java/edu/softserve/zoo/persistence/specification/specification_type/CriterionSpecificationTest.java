package edu.softserve.zoo.persistence.specification.specification_type;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.stubs.criterion.StubGetAllEmployeesCriterion;
import edu.softserve.zoo.persistence.specification.stubs.criterion.StubGetEmployeeByIdCriterion;
import edu.softserve.zoo.persistence.specification.stubs.criterion.StubGetEmployeesByFirstNameCriterion;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/persistence-test-ctx.xml")
public class CriterionSpecificationTest extends AbstractSpecificationTypeTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Collection<Employee> performFindAllQuery() {
        return employeeRepository.find(new StubGetAllEmployeesCriterion());
    }

    @Override
    public Collection<Employee> performFindByIdCorrectQuery() {
        return employeeRepository.find(new StubGetEmployeeByIdCriterion(AbstractSpecificationTypeTest.CORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByIdIncorrectQuery() {
        return employeeRepository.find(new StubGetEmployeeByIdCriterion(AbstractSpecificationTypeTest.INCORRECT_ID));
    }

    @Override
    public Collection<Employee> performFindByNameCorrectQuery() {
        return employeeRepository.find(
                new StubGetEmployeesByFirstNameCriterion(AbstractSpecificationTypeTest.CORRECT_FIRST_NAME));
    }

    @Override
    public Collection<Employee> performFindByNameIncorrectQuery() {
        return employeeRepository.find(
                new StubGetEmployeesByFirstNameCriterion(AbstractSpecificationTypeTest.INCORRECT_FIRST_NAME));
    }
}
