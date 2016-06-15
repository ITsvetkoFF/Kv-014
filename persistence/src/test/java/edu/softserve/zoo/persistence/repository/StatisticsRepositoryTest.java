package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.persistence.config.PersistenceConfig;
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
@ContextConfiguration(classes = PersistenceConfig.class)
@ActiveProfiles("test")
@Transactional
public class StatisticsRepositoryTest {
    @Autowired
    StatisticsRepository statisticsRepository;

    @Test
    public void fedAnimals() {
        Long fedAnimals = statisticsRepository.getFedAnimals();
        Assert.notNull(fedAnimals, "Fed animals count has to be not null");
        assertEquals("Wrong fed animals count", new Long(3), fedAnimals);
    }
}
