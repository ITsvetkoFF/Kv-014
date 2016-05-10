package edu.softserve.zoo.service;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.repository.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public abstract class CrudServiceImplTest<T extends BaseEntity> {

    private T expected;
    private T actual;

    public abstract T getExpected();

    public abstract T getActual();

    public abstract Repository<T> getRepository();

    public abstract Service<T> getService();

    @Before
    public void setUp() throws Exception {
        expected = getExpected();
        actual = getActual();
    }

    @Test
    public void testSave() throws Exception {
        when(getRepository().save(actual)).thenReturn(actual);

        Assert.assertEquals(expected, getService().save(actual));
    }

    @Test
    public void testUpdate() throws Exception {
        when(getRepository().update(actual)).thenReturn(actual);

        Assert.assertEquals(expected, getService().update(actual));
    }

    @Test
    public void testDelete() throws Exception {
        getService().delete(actual);
        verify(getRepository()).delete(actual);
    }


}
