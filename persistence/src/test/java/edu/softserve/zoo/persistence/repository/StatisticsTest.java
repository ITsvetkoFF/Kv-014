package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class StatisticsTest {
    @Autowired
    StatisticsRepository repo;

    @Test
    @Transactional(readOnly = true)
    public void shouldPass() {
        final  Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(2L);
        assertEquals("Each employee has to have tasks", 2, repo.getEmployeeTasksStatuses(employee).size());
        assertEquals("Each employee has to have tasks", 1, repo.getEmployeeTasksTypes(employee).size());
        assertEquals("Statistics cannot be null", new Statistics(12L, 12L, 3L), repo.getZooStatistics());
        assertEquals("Percentage have to be 10/12", 10.0/12, repo.getFedAnimalsPercentage(), 1.0/100);
    }
}
