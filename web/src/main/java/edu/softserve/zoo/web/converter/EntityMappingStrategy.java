package edu.softserve.zoo.web.converter;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.web.dto.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Entity mapping strategy
 *
 * @author Serhii Alekseichenko
 */
@Component
class EntityMappingStrategy implements MappingStrategy {
    @Autowired
    private ModelConverter converter;


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(Object object) {
        return BaseEntity.class.isAssignableFrom(object.getClass()) || BaseDto.class.isAssignableFrom(object.getClass());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToDto(Object entity) {
        if (ModelConverter.isProxy(entity.getClass())) {
            BaseDto emptyDto = converter.getDto((BaseEntity) entity);
            Long id = ((BaseEntity) entity).getId();
            emptyDto.setId(id);
            return emptyDto;
        } else return converter.convertToDto((BaseEntity) entity);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToEntity(Object object) {
        return converter.convertToEntity((BaseDto) object);
    }
}
