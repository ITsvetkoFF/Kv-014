package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.util.AppProfiles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles(AppProfiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public abstract class AbstractRepositoryTest<T extends BaseEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.MANDATORY)
    public T findOne(Specification<T> specification, Long expectedId) {
        T actual = getPersistenceProvider().findOne(specification);
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
        return actual;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T testSave(T validEntity, Long expectedId) {
        PersistenceProvider<T> persistenceProvider = getPersistenceProvider();
        persistenceProvider.save(validEntity);
        sessionFactory.getCurrentSession().evict(validEntity);
        T savedEntity = persistenceProvider.findOne(new GetByIdSpecification<>(getType(), validEntity.getId()));
        assertNotNull(savedEntity);
        assertEquals(expectedId, savedEntity.getId());
        return savedEntity;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void testDelete(Long id) {
        PersistenceProvider<T> persistenceProvider = getPersistenceProvider();
        assertTrue(persistenceProvider.delete(id, getType()));
        assertTrue(persistenceProvider.findOne(new GetByIdSpecification<>(getType(), id)) == null);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T testUpdate(T entity) {
        getPersistenceProvider().update(entity);
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.flush();
        currentSession.evict(entity);

        return findOne(new GetByIdSpecification<>(getType(), entity.getId()), entity.getId());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void testFind(Specification<T> specification, int expectedSize) {
        List<T> entities = getPersistenceProvider().find(specification);
        assertNotNull(entities);
        assertTrue(entities.size() == expectedSize);
    }

    protected abstract PersistenceProvider<T> getPersistenceProvider();

    protected abstract Class<T> getType();

}
