package edu.softserve.zoo.web.converter.mapping;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.web.dto.BaseDto;

/**
 * Specifies the relationships between Entities and DTOs.
 *
 * @author Andrii Abramov on 5/29/16.
 */
public interface DtoMapper {
    Class<? extends BaseEntity> getEntityClass(Class<?> forClass);

    Class<? extends BaseDto> getDtoClass(Class<?> forClass);
}
