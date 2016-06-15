package edu.softserve.zoo.persistence.provider.specification_processing.provider.impl;

import edu.softserve.zoo.core.exceptions.ApplicationException;
import edu.softserve.zoo.core.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.core.util.Validator;
import edu.softserve.zoo.persistence.provider.specification_processing.provider.ProcessingStrategyProvider;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of a {@link ProcessingStrategyProvider}.
 *
 * @author Bohdan Cherniakh
 */
@Component
public class ProcessingStrategyProviderImpl<T extends BaseEntity> implements ProcessingStrategyProvider<T> {

    @Autowired
    private Map<String, SpecificationProcessingStrategy<T>> supportedProcessingStrategies;

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecificationProcessingStrategy<T> getStrategy(Specification<T> specification) {
        String specificationName = getSpecificationType(specification).getSimpleName();
        return supportedProcessingStrategies.get(specificationName);
    }

    private Class<?> getSpecificationType(Specification<T> specification) {

        Set<Class<?>> supportedSpecificationTypes = getSupportedSpecificationTypes(specification);

        //TODO add reason when reasons are done
        Validator.isTrue(supportedSpecificationTypes.size() == 1,
                ApplicationException.getBuilderFor(PersistenceException.class)
                        .withMessage("Unable to find strategy for: " + specification.getClass().getSimpleName())
                        .build());

        return supportedSpecificationTypes.stream().findFirst().get();
    }

    private Set<Class<?>> getSupportedSpecificationTypes(Specification<T> specification) {
        Set<Class<?>> specificationInterfaces = getSpecificationInterfaces(specification);
        return specificationInterfaces.stream()
                .filter(aClass -> supportedProcessingStrategies.keySet().contains(aClass.getSimpleName()))
                .collect(Collectors.toSet());
    }

    private Set<Class<?>> getSpecificationInterfaces(Specification<T> specification) {
        return ClassUtils.getAllInterfacesAsSet(specification);
    }
}
