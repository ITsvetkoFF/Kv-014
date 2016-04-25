package edu.softserve.zoo.persistence.specification;

/**
 * Interface that defines the specification API.
 * Should provide the data for the {@link edu.softserve.zoo.persistence.repository.Repository#find(Specification)}
 */
//TODO - T should extend BaseEntity.
public interface Specification<T> {

    /**
     * <p>Defines the API for the query which is used in {@link edu.softserve.zoo.persistence.repository.Repository} and
     * {@link edu.softserve.zoo.persistence.provider.PersistenceProvider}</p>
     * @return the query definition.
     */
    Object query();
}
