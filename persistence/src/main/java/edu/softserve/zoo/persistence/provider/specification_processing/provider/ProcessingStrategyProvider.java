package edu.softserve.zoo.persistence.provider.specification_processing.provider;

import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;

/**
 * <P>Interface that defines processing strategy choosing by some particular specification</P>
 *
 * @param <T> the type of the domain objects which are been processed.
 * @author Bohdan Cherniakh
 */
public interface ProcessingStrategyProvider<T extends BaseEntity> {
    /**
     * Return the corresponding implementation of the {@link SpecificationProcessingStrategy}
     * for defined {@link Specification}
     *
     * @param specification specification that should be processed.
     * @return appropriate implementation of the {@link SpecificationProcessingStrategy}
     * @throws {@link PersistenceException} if no {@link SpecificationProcessingStrategy}
     *                implementation found.
     */
    SpecificationProcessingStrategy<T> getStrategy(Specification<T> specification);

}
