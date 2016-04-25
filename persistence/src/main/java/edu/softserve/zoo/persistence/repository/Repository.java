package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.exception.PersistenceException;
import edu.softserve.zoo.persistence.specification.Specification;

import java.util.Collection;

/**
 * <p>Object that represents Repository. Works with the domain objects of the given type.</p>
 * <p>Repository implements basic CRUD operations for domain objects called entities.
 * The find operation is implemented using {@link Specification}</p>
 *
 * @param <T> the type of the domain objects which are stored.
 */
public interface Repository<T extends BaseEntity> {

    /**
     * Saves the entity into the repository.
     *
     * @param entity - an object that should be stored.
     * @throws PersistenceException given the information about the problem that occurred with the storage.
     */
    void save(T entity);

    /**
     * Deletes the entity from the repository
     *
     * @param entity - object that should be deleted.
     * @throws PersistenceException given the information about the problem that occurred with the storage.
     */
    void delete(T entity);

    /**
     * Updates the entity state in the repository.
     *
     * @param entity - object which state should be updated.
     * @throws PersistenceException given the information about the problem that occurred with the storage.
     */
    void update(T entity);

    /**
     * Finds entities in the repository according to given Specification.
     *
     * @param specification defines restrictions for performed search.
     * @return The collection of domain objects or null if there are no objects in the database that match the query.
     * @throws PersistenceException given the information about the problem that occurred with the storage.
     * @see Specification
     */
    Collection<T> find(Specification<T> specification);

}
