package edu.softserve.zoo.persistence.specification.hibernate.composite;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.EntityField;

import java.util.Set;


/**
 * Abstract implementation of the {@link CompositeSpecification}.
 * Help to implement concrete composite specifications.
 *
 * @author Vadym Holub
 */
public abstract class AbstractCompositeSpecification<T extends BaseEntity> implements CompositeSpecification<T> {

    private Set<? extends EntityField<T>> fields;

    public Set<? extends EntityField<T>> getFields() {
        return fields;
    }

    public void setEntityFields(Set<? extends EntityField<T>> fields) {
        this.fields = fields;
    }

}
