package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/persistence-test-ctx.xml")
@Transactional(readOnly = true)
public class StatisticsTest {
    @Autowired
    StatisticsRepository repo;

    @Test
    public void shouldPass() {
        assertEquals("Each employee has to have tasks", 2, repo.getEmployeeTasksStatuses(2L).size());
        assertEquals("Each employee has to have tasks", 1, repo.getEmployeeTasksTypes(2L).size());
        assertEquals("Statistics cannot be null", new HashMap<String, Long>() {{
            put("animalsCount", 12L);
            put("housesCount", 12L);
            put("employeesCount", 3L);
        }}, repo.getZooStatistics());
        assertEquals("Percentage have to be 10/12", 10.0/12, repo.getFedAnimalsPercentage(), 1.0/100);
    }

    @Test(expected = NotFoundException.class)
    public void taskStatusInvalidEmployeeId() {
        repo.getEmployeeTasksStatuses(12L);
    }

    @Test(expected = NotFoundException.class)
    public void taskTypeInvalidEmployeeId() {
        repo.getEmployeeTasksTypes(12L);
    }
}
