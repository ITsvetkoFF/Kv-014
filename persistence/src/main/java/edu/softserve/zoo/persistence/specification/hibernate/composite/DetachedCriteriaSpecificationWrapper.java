package edu.softserve.zoo.persistence.specification.hibernate.composite;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Vadym Holub
 */
public class DetachedCriteriaSpecificationWrapper<T extends BaseEntity> implements DetachedCriteriaSpecification<T> {

    private final DetachedCriteria query;

    public DetachedCriteriaSpecificationWrapper(DetachedCriteria query) {
        this.query = query;
    }

    @Override
    public DetachedCriteria query() {
        return query;
    }
}
