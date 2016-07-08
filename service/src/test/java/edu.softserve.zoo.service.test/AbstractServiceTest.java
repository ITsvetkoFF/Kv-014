package edu.softserve.zoo.service.test;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.config.ServiceConfig;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Vadym Holub
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@Transactional
public abstract class AbstractServiceTest<T extends BaseEntity> {

    public T primaryFindOneTest(Long id) {
        T actual = getService().findOne(id);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
        return actual;
    }

    public void primaryFindAllTest(int expectedSize) {
        List<T> entities = getService().findAll();
        assertNotNull(entities);
        assertEquals(entities.size(), expectedSize);
    }

    protected abstract Service<T> getService();
}
