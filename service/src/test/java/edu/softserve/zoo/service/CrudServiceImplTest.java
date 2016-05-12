package edu.softserve.zoo.service;

import edu.softserve.zoo.model.BaseEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test.xml")
public abstract class CrudServiceImplTest<E extends BaseEntity> {

    protected abstract Service<E> getService();

    protected abstract E testEntity();

    @Test
    public void testSave() throws Exception {
        E saved = getService().save(testEntity());
        Assert.assertEquals(Integer.valueOf(15), saved.getId());
    }
}
