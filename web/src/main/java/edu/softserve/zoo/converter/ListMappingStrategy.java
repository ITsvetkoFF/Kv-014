package edu.softserve.zoo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List mapping strategy
 *
 * @author Serhii Alekseichenko
 */
@Component
class ListMappingStrategy implements MappingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelConverter.class);

    @Autowired
    private ModelConverter converter;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(Object object) {
        return List.class.isAssignableFrom(object.getClass());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToDto(Object object) {
        try {
            return converter.convertToDtoCollection((Collection) object).collect(Collectors.toList());
        } catch (RuntimeException e) {
            LOGGER.trace("Lazy Persistent List");
            return null;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToEntity(Object object) {
        return converter.convertToEntitiesCollection((Collection) object).collect(Collectors.toList());
    }
}
