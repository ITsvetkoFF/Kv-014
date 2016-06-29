package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.runner.RunWith;
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

    @Transactional(propagation = Propagation.MANDATORY)
    public T findOne(Specification<T> specification, Long expectedId) {
        T actual = getRepository().findOne(specification);
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
        return actual;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T save(T validEntity, Long expectedId) {
        Repository<T> repository = getRepository();
        repository.save(validEntity);
        T savedEntity = repository.findOne(new GetByIdSpecification<>(getType(), validEntity.getId()));
        assertNotNull(savedEntity);
        assertEquals(expectedId, savedEntity.getId());
        return savedEntity;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(Long id) {
        Repository<T> repository = getRepository();
        assertTrue(repository.delete(id, getType()));
        assertTrue(repository.findOne(new GetByIdSpecification<>(getType(), id)) == null);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        getRepository().update(entity);
        return findOne(new GetByIdSpecification<>(getType(), entity.getId()), entity.getId());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void find(Specification<T> specification, int expectedSize) {
        List<T> entities = getRepository().find(specification);
        assertNotNull(entities);
        assertEquals(entities.size(), expectedSize);
    }

    protected abstract Repository<T> getRepository();

    protected abstract Class<T> getType();

}
