package edu.softserv.zoo.persistence;

import edu.softserv.zoo.persistence.exceptions.PersistenceException;
import edu.softserv.zoo.persistence.specification.Specification;

import java.io.Serializable;

/**
 * Interface name: Repository
 * Version: 1.0
 * Created: 19.04.2016
 */
public interface Repository <T extends BaseEntity, K extends Serializable> {
    void save(T entity) throws PersistenceException, PersistenceException;
    void delete(T entity) throws PersistenceException;
    void update(T entity) throws PersistenceException;

    T findOne(K id) throws PersistenceException;
    Iterable<T> findAll() throws PersistenceException;
    Iterable<T> query(Specification<T> specification);

}
