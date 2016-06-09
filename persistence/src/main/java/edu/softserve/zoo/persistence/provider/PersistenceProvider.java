package edu.softserve.zoo.persistence.provider;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;

import java.util.List;

/**
 * Interface that defines the persistent storage interface.
 *
 * @param <T> the type of the domain objects which are stored.
 * @author Bohdan Cherniakh
 */
public interface PersistenceProvider<T extends BaseEntity> {

    /**
     * Finds domain object in the persistent storage according to given Specification.
     *
     * @param specification defines restrictions for performed search.
     * @return domain object by defined restrictions or null if not found
     */
    T findOne(Specification<T> specification);

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
     * @param id   of domain object that should be deleted.
     * @param type of domain object that should be deleted.
     */
    boolean delete(Long id, Class<T> type);

    /**
     * Finds the collection of domain objects in the persistent storage. The search criteria is defined by the
     * Specification object.
     *
     * @param specification the {@link Specification} object that describes the query that should be performed.
     * @return The {@link List} of domain objects or empty list if there are no objects in the database that match the query.
     * @see Specification
     */
    List<T> find(Specification<T> specification);
}
