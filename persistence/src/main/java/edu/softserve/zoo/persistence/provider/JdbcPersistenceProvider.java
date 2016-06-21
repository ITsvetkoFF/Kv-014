package edu.softserve.zoo.persistence.provider;

import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;

import java.util.List;
import java.util.function.Function;

/**
 * Persistence provider for native sql queries and jdbc processing
 *
 * @author Taras Zubrei
 */
public interface JdbcPersistenceProvider {
    /**
     * Finds the collection of objects in the persistent storage. The search criteria is defined by the
     * JdbcSpecification object.
     *
     * @param specification the {@link JdbcSpecification} object that describes the query that should be performed.
     * @param processor     the function that converts jdbc result into needed object.
     * @return result list which each element of was processed by {@code processor} function.
     * @see JdbcSpecification
     */
    <T, K> List<T> findAll(JdbcSpecification<K> specification, Function<K, T> processor);

    /**
     * Finds object in the persistent storage according to given Specification.
     *
     * @param specification the {@link JdbcSpecification} object that describes the query that should be performed.
     * @param processor     the function that converts jdbc result into needed object.
     * @return object by defined restrictions and processed with function.
     */
    <T, K> T findOne(JdbcSpecification<K> specification, Function<K, T> processor);
}
