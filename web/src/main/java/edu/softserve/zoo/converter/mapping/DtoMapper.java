package edu.softserve.zoo.converter.mapping;

import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.model.BaseEntity;

/**
 * Specifies the relationships between Entities and DTOs.
 *
 * @author Andrii Abramov on 5/29/16.
 */
public interface DtoMapper {
    Class<? extends BaseEntity> getEntityClass(Class<?> forClass);

    Class<? extends BaseDto> getDtoClass(Class<?> forClass);
}
