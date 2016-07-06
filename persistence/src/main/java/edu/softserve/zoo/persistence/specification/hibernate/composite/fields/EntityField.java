package edu.softserve.zoo.persistence.specification.hibernate.composite.fields;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.specification_processing.builder.CompositeSpecificationBuilder;

/**
 * EntityField represents field of domain object, it is used by {@link CompositeSpecificationBuilder}
 * to choose which field should be retrieved
 *
 * @author Vadym Holub
 */
public interface EntityField<T extends BaseEntity> {
    String getName();
}