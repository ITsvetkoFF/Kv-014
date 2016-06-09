package edu.softserve.zoo.persistence.provider.specification_processing.strategy;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.specification.Specification;

import java.util.List;

/**
 * Interface defines the {@link Specification} processing strategy.
 *
 * @author Bohdan Cherniakh
 */
public interface SpecificationProcessingStrategy<T extends BaseEntity> {

    /**
     * Processes the {@link Specification} using {@link PersistenceProvider} implementation.
     *
     * @param specification {@link Specification} for processing.
     * @return collection of domain objects.
     */
    List<T> process(Specification<T> specification);
}
