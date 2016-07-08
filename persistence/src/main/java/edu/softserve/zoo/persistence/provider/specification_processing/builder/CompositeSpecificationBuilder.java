package edu.softserve.zoo.persistence.provider.specification_processing.builder;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.CompositeSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.EntityField;

/**
 * CompositeSpecificationBuilder modifies specification using specified {@link EntityField}
 * to retrieve domain object with requested fields.
 *
 * @author Vadym Holub
 */
public interface CompositeSpecificationBuilder<T extends BaseEntity> {

    /**
     * Modify specification according to assigned EntityFields in CompositeSpecification
     *
     * @param specification that should be processed.
     * @return modified specification
     */
    Specification<T> build(CompositeSpecification<T> specification);
}
