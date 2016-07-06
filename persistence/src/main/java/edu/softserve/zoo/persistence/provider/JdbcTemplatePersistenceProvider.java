package edu.softserve.zoo.persistence.provider;

import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.specification.hibernate.SqlGetMapSpecification;

import java.util.Map;

/**
 * JdbcTemplate based persistence provider for native SQL queries
 *
 * @author Serhii Alekseichenko
 */
@FunctionalInterface
public interface JdbcTemplatePersistenceProvider {

    /**
     * Get parametrized map from the persistent storage according to given Specification.
     * In most cases key is an Id and value is a result of an aggregation function
     *
     * @param specification defines restrictions for performed search.
     * @return map of Key-Values pairs
     * @throws PersistenceProviderException if the received specification is null
     */
    <K extends Number, V extends Number> Map<K, V> getMap(SqlGetMapSpecification<K, V> specification);

}
