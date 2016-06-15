package edu.softserve.zoo.service;
import edu.softserve.zoo.core.exceptions.NotFoundException;
import edu.softserve.zoo.core.model.BaseEntity;

import java.util.List;

public interface Service<T extends BaseEntity> {

    /**
     * Returns domain object by id from persistent storage
     *
     * @param id identifier of required entity
     * @return entity with specified identifier.
     * @throws NotFoundException if entity not found with given id
     */
    T findOne(Long id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    void delete(Long id);

}