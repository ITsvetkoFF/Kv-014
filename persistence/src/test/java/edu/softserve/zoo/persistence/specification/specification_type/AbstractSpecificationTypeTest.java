package edu.softserve.zoo.persistence.specification.specification_type;

import edu.softserve.zoo.model.Employee;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Bohdan Cherniakh
 */
public abstract class AbstractSpecificationTypeTest {
    public static final Integer CORRECT_ID = 2;
    public static final Integer INCORRECT_ID = 1984;

    public static final String CORRECT_FIRST_NAME = "BOHDAN";
    public static final String INCORRECT_FIRST_NAME = "VASISUALIY";

    @Test
    @Transactional
    public void shouldCollectAllEmployees() {
        int expectedCount = 3;
        Collection<Employee> employees = performFindAllQuery();
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
    }

    public abstract Collection<Employee> performFindAllQuery();

    @Test
    @Transactional
    public void shouldCollectOneEmployeeById() {
        int expectedCount = 1;
        String expectedEmployeeEmail = "B.CHERNIAKH@GMAIL.COM";

        Collection<Employee> employees = performFindByIdCorrectQuery();
        int actualCount = employees.size();
        assertEquals("Should collect one and only employee", expectedCount, actualCount);
        String actualEmployeeEmail = employees.stream().findFirst().get().getEmail();
        assertEquals("Should be the same", expectedEmployeeEmail, actualEmployeeEmail);
    }

    public abstract Collection<Employee> performFindByIdCorrectQuery();

    @Test
    @Transactional
    public void shouldFailToCollectAnyEmployeeById() {
        int expectedCount = 0;

        Collection<Employee> employees = performFindByIdIncorrectQuery();
        int actualCount = employees.size();
        assertEquals("Should not collect any employees", expectedCount, actualCount);
    }

    public abstract Collection<Employee> performFindByIdIncorrectQuery();

    @Test
    @Transactional
    public void shouldCollectOneEmployeeByFirstName() {
        int expectedCount = 1;
        String expectedEmployeeEmail = "B.CHERNIAKH@GMAIL.COM";

        Collection<Employee> employees = performFindByNameCorrectQuery();
        int actualCount = employees.size();
        assertEquals("Should collect one employee with id = 2", expectedCount, actualCount);
        String actualEmployeeEmail = employees.stream().findFirst().get().getEmail();
        assertEquals("Emails should be the same", expectedEmployeeEmail, actualEmployeeEmail);
    }

    public abstract Collection<Employee> performFindByNameCorrectQuery();

    @Test
    @Transactional
    public void shouldFailToCollectAnyEmployeeByFirstName() {
        int expectedCount = 0;
        Collection<Employee> employees = performFindByNameIncorrectQuery();
        int actualCount = employees.size();
        assertEquals("Should not collect any employees", expectedCount, actualCount);
    }

    public abstract Collection<Employee> performFindByNameIncorrectQuery();
}
