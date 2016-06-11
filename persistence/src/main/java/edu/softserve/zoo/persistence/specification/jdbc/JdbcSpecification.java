package edu.softserve.zoo.persistence.specification.jdbc;

import edu.softserve.zoo.persistence.specification.Specification;

/**
 * Specification for native sql queries and jdbc processing
 *
 * @author Taras Zubrei
 */
public interface JdbcSpecification<T> extends Specification {
    /**
     * <p>Defines the API for the native SQL query which is used by {@link edu.softserve.zoo.persistence.repository.Repository} and
     * {@link edu.softserve.zoo.persistence.provider.impl.JdbcPersistenceProvider}</p>
     *
     * @return the query definition.
     */
    String query();
}
