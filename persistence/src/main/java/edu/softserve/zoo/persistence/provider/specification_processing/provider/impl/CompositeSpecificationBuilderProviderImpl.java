package edu.softserve.zoo.persistence.provider.specification_processing.provider.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.exception.CompositeSpecificationBuilderProviderException;
import edu.softserve.zoo.persistence.provider.specification_processing.builder.CompositeSpecificationBuilder;
import edu.softserve.zoo.persistence.provider.specification_processing.provider.CompositeSpecificationBuilderProvider;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Vadym Holub
 */
@Component
public class CompositeSpecificationBuilderProviderImpl<T extends BaseEntity> implements CompositeSpecificationBuilderProvider<T> {


    @Autowired
    private Map<String, CompositeSpecificationBuilder<T>> specificationBuilders;

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeSpecificationBuilder<T> getBuilderFor(SpecificationProcessingStrategy<T> strategy) {

        CompositeSpecificationBuilder<T> specificationBuilder = specificationBuilders.get(strategy.getClass().getSimpleName());

        Validator.notNull(specificationBuilder, ApplicationException.getBuilderFor(CompositeSpecificationBuilderProviderException.class)
                .withMessage(String.format("unsupported strategy '%s'", strategy.getClass().getSimpleName()))
                .forReason(CompositeSpecificationBuilderProviderException.Reason.UNSUPPORTED_STRATEGY)
                .build());

        return specificationBuilder;
    }
}
