package edu.softserve.zoo.persistence.provider.specification_processing.provider;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.exception.CompositeSpecificationBuilderProviderException;
import edu.softserve.zoo.persistence.provider.specification_processing.builder.CompositeSpecificationBuilder;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;

/**
 * Provide  {@link CompositeSpecificationBuilder} for appropriate {@link SpecificationProcessingStrategy}
 *
 * @author Vadym Holub
 */
public interface CompositeSpecificationBuilderProvider<T extends BaseEntity> {

    /**
     * Return the corresponding implementation of the {@link CompositeSpecificationBuilder}
     * for defined {@link SpecificationProcessingStrategy}
     *
     * @param strategy that should be processed.
     * @return appropriate implementation of the {@link CompositeSpecificationBuilder}
     * @throws CompositeSpecificationBuilderProviderException if appropriate strategies are not been found
     */

    CompositeSpecificationBuilder<T> getBuilderFor(SpecificationProcessingStrategy<T> strategy);
}
