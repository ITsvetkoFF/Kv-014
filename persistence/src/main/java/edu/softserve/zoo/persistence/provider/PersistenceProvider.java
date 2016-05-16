package edu.softserve.zoo.persistence.provider;

import edu.softserve.zoo.persistence.specification.Specification;

import java.util.List;

/**
 * Interface that defines the persistent storage interface.
 *
 * @param <T> the type of the domain objects which are stored.
 * @author Bohdan Cherniakh
 */
public interface PersistenceProvider<T> {

    /**
     * Retrieves entity by id from persistent storage.
     *
     * @param id identifier of required entity
     * @param type type of required entity
     * @return entity with specified identifier.
     * @throws edu.softserve.zoo.persistence.exception.NotFoundException if entity not found with given id
     */
    T findOne(Long id, Class<T> type);

    /**
     * Saves the given entity into the persistent storage.
     *
     * @param entity an object that should be saved.
     * @return persisted entity with generated identifier.
     */
    T save(T entity);

    /**
     * Updates a state of the given entity in the persistent storage.
     *
     * @param entity domain object that should be updated.
     * @return entity with updated state.
     */
    T update(T entity);

    /**
     * Deletes the given entity from the persistent storage.
     *
     * @param id of domain object that should be deleted.
     * @param type of domain object that should be deleted.
     */
    boolean delete(Long id, Class<T> type);

    /**
     * Finds the collection of domain objects in the persistent storage. The search criteria is defined by the
     * Specification object.
     *
     * @param specification the {@link Specification} object that describes the query that should be performed.
     * @return The {@link List} of domain objects or null if there are no objects in the database that match the query.
     * @see Specification
     */
    List<T> find(Specification<T> specification);
}
