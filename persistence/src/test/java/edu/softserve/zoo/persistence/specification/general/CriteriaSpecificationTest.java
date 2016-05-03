package edu.softserve.zoo.persistence.specification.general;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.repository.impl.AnimalRepository;
import edu.softserve.zoo.persistence.repository.impl.EmployeeRepository;
import edu.softserve.zoo.persistence.specification.hibernate.CriteriaSpecification;
import edu.softserve.zoo.persistence.specification.stubs.criteria.GetAllEmployeesCriteriaStub;
import edu.softserve.zoo.persistence.specification.stubs.criteria.GetEmployeeByIdCriteriaStub;
import edu.softserve.zoo.persistence.specification.stubs.criteria.GetEmployeeByNameCriteriaStub;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class CriteriaSpecificationTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AnimalRepository animalRepository;

    @Test
    @Transactional
    public void getAllTest() {
        int expectedCount = 3;
        List<Employee> employees = getList(employeeRepository.find(new GetAllEmployeesCriteriaStub()));
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

        List<Employee> employees = getList(employeeRepository.find(new GetEmployeeByIdCriteriaStub(employeeId)));
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

        List<Employee> employees = getList(employeeRepository.find(new GetEmployeeByIdCriteriaStub(employeeId)));
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
                employeeRepository.find(new GetEmployeeByNameCriteriaStub(employeeFirstName)));
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
        List<Employee> employees = getList(employeeRepository.find(new GetEmployeeByNameCriteriaStub(employeeFirstName)));
        int actualCount = employees.size();
        assertEquals("Should collect all employees", expectedCount, actualCount);
    }

    @Test
    @Transactional
    public void getAllAnimalsTest() {
        int expectedCount = 12;
        List<Animal> employees = getList(animalRepository.find(new CriteriaSpecification<Animal>() {
            @Override
            public Class<Animal> getType() {
                return Animal.class;
            }

            @Override
            public Criterion query() {
                return Restrictions.isNotNull("id");
            }
        }));
        int actualCount = employees.size();
        employees.forEach(System.out::println);
        assertEquals("Should collect all animals", expectedCount, actualCount);
    }

    private List getList(Collection collection) {
        return new LinkedList<>(collection);
    }


}
