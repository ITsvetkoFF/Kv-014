package edu.softserve.zoo.web.converter;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.web.dto.BaseDto;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Simple fields mapping strategy
 *
 * @author Serhii Alekseichenko
 */
@Component
class SimpleFieldMappingStrategy implements MappingStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(Object object) {
        return !(Collection.class.isAssignableFrom(object.getClass())
                || BaseEntity.class.isAssignableFrom(object.getClass())
                || BaseDto.class.isAssignableFrom(object.getClass()));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToDto(Object object) {
        return object;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToEntity(Object object) {
        return object;
    }
}
