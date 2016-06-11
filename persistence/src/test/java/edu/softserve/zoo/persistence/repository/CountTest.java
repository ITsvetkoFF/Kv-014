package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.persistence.config.PersistenceTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
@ActiveProfiles("test")
@Transactional
public class CountTest {
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void count() {
        Long animalCount = animalRepository.count();
        Long houseCount = houseRepository.count();
        Long employeeCount = employeeRepository.count();
        Assert.notNull(animalCount, "Animals count has to be not null");
        Assert.notNull(houseCount, "Houses count has to be not null");
        Assert.notNull(employeeCount, "Employees count has to be not null");
        assertEquals("Wrong animals count", new Long(12L), animalCount);
        assertEquals("Wrong houses count", new Long(12L), houseCount);
        assertEquals("Wrong employees count", new Long(3L), employeeCount);
    }
}
