package edu.softserve.zoo.persistence.specification.general;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.stubs.hql.GetAllEmployeesHQLStub;
import edu.softserve.zoo.persistence.specification.stubs.hql.GetEmployeeByIdHQLStub;
import edu.softserve.zoo.persistence.specification.stubs.hql.GetEmployeeByNameHQLStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class HQLSpecificationTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    @Transactional
    public void getAllTest() {
        int expectedCount = 3;
        List<Employee> employees = getList(repository.find(new GetAllEmployeesHQLStub()));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
        employees.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void getByIdCorrectId() {
        Integer employeeId = 2;
        int expectedCount = 1;
        String expectedEmployeeEmail = "B.CHERNIAKH@GMAIL.COM";

        List<Employee> employees = getList(repository.find(new GetEmployeeByIdHQLStub(employeeId)));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
        String actualEmployeeEmail = employees.get(0).getEmail();
        assertEquals("Emails should be the same", expectedEmployeeEmail, actualEmployeeEmail);
    }

    @Test
    @Transactional
    public void getByIdIncorrectId() {
        Integer employeeId = 1984;
        int expectedCount = 0;

        List<Employee> employees = getList(repository.find(new GetEmployeeByIdHQLStub(employeeId)));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
    }

    @Test
    @Transactional
    public void getByParameterCorrect() {
        String employeeFirstName = "BOHDAN";
        int expectedCount = 1;
        String expectedEmployeeEmail = "B.CHERNIAKH@GMAIL.COM";

        List<Employee> employees = getList(
                repository.find(new GetEmployeeByNameHQLStub(employeeFirstName)));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
        String actualEmployeeEmail = employees.get(0).getEmail();
        assertEquals("Emails should be the same", expectedEmployeeEmail, actualEmployeeEmail);
    }

    @Test
    @Transactional
    public void getByParameterIncorrect() {
        String employeeFirstName = "Vaska";
        int expectedCount = 0;
        List<Employee> employees = getList(repository.find(new GetEmployeeByNameHQLStub(employeeFirstName)));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
    }

    private List<Employee> getList(Collection<Employee> collection) {
        return new LinkedList<>(collection);
    }
}
