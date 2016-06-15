package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>Abstract implementation of the <tt>Repository</tt>. Help to implement concrete repositories for every
 * domain object. Implements routine CRUD operations.</p>
 *
 * @param <T> type of the domain object.
 * @author Bohdan Cherniakh
 */
public abstract class AbstractRepository<T extends BaseEntity> implements Repository<T> {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private PersistenceProvider<T> persistenceProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(Specification<T> specification) {
        return persistenceProvider.findOne(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        return persistenceProvider.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id, Class<T> type) {
        return persistenceProvider.delete(id, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        return persistenceProvider.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Specification<T> specification) {
        return persistenceProvider.find(specification);
    }
}
