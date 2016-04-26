package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * <p>Abstract implementation of the <tt>Repository</tt>. Help to implement concrete repositories for every
 * domain object. Implements routine CRUD operations.</p>
 *
 * @author Bohdan Cherniakh
 * @param <T> type of the domain object.
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
    public T save(T entity) {
        return persistenceProvider.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T entity) {
        persistenceProvider.delete(entity);
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
    public Collection<T> find(Specification<T> specification) {
        return persistenceProvider.find(specification);
    }
}
