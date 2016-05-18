package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.impl.GetAllSpecification;
import edu.softserve.zoo.service.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vadym Holub
 */

public abstract class AbstractService<T extends BaseEntity> implements Service<T> {

    @Transactional
    @Override
    public T findOne(Long id, Class<T> type) {
        return getRepository().findOne(id, type);
    }

    @Transactional
    @Override
    public List<T> findAll(Class<T> type) {
        return getRepository().find(new GetAllSpecification<>(type));
    }

    @Transactional
    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public T update(T entity) {
        return getRepository().update(entity);
    }

    @Transactional
    @Override
    public void delete(Long id, Class<T> type) {
        getRepository().delete(id,type);
    }

    abstract Repository<T> getRepository();
}
