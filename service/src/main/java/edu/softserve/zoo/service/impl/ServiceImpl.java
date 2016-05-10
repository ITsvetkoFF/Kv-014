package edu.softserve.zoo.service.impl;


import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.Service;
import org.springframework.transaction.annotation.Transactional;

public abstract class ServiceImpl<T extends BaseEntity> implements Service<T> {

    @Transactional
    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Transactional
    @Override
    public T update(T entity) {
        return getRepository().update(entity);
    }

    public abstract void deleteById(Integer id);

    abstract Repository<T> getRepository();
}
