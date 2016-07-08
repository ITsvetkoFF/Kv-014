package edu.softserve.zoo.persistence.specification.hibernate.composite;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.EntityField;

import java.util.Set;

/**
 * CompositeSpecification allows modifying specifications for {@link Repository}
 * by adding {@link EntityField} objects as desired properties of a domain object
 *
 * @author Vadym Holub
 */
public interface CompositeSpecification<T extends BaseEntity> extends Specification<T> {

    Set<? extends EntityField<T>> getFields();

    void setEntityFields(Set<? extends EntityField<T>> fields);

    Class<T> getEntityType();

}
