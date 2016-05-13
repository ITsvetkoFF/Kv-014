package edu.softserve.zoo.persistence.provider;

import edu.softserve.zoo.persistence.specification.Specification;

import java.util.List;

/**
 * Interface defines the {@link Specification} processing strategy.
 *
 * @author Bohdan Cherniakh
 */
public interface SpecificationProcessingStrategy<T> {

    /**
     * Processes the {@link Specification} using {@link PersistenceProvider} implementation.
     *
     * @param specification {@link Specification} for processing.
     * @return collection of domain objects.
     */
    List<T> process(Specification<T> specification);
}
