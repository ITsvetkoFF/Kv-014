package edu.softserv.zoo.persistence.providers;

import edu.softserv.zoo.persistence.exceptions.PersistenceException;
import edu.softserv.zoo.persistence.specification.Specification;

import java.io.Serializable;

/**
 * Interface name: PersistenceProvider
 * Version: 1.0
 * Created: 19.04.2016
 */
public interface PersistenceProvider<E, K extends Serializable> {

    void create(E entity) throws PersistenceException;

    void update(E entity) throws PersistenceException;

    void delete(E entity) throws PersistenceException;

    E read(K key) throws PersistenceException;

    Iterable<E> readAll() throws PersistenceException;

    Iterable<E> query(Specification<E> specification);
}
