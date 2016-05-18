package edu.softserve.zoo.service;
import edu.softserve.zoo.model.BaseEntity;

import java.util.List;

public interface Service<T extends BaseEntity> {

    T findOne(Long id, Class<T> type);

    List<T> findAll(Class<T> type);

    T save(T entity);

    T update(T entity);

    void delete(Long id, Class<T> type);

}