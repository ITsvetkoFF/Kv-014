package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ilya Doroshenko
 */
public class HouseSpecification implements DetachedCriteriaSpecification<House> {
    private static final String ZONE_ID = "zone.id";
    private static final String MAX_CAPACITY = "maxCapacity";

    private Long zoneId;
    private Integer maxCapacity;

    public HouseSpecification() {
    }

    @Override
    public DetachedCriteria query() {
        DetachedCriteria criteria = DetachedCriteria.forClass(House.class);

        addRestrictionIfNotNull(criteria, MAX_CAPACITY, maxCapacity);
        addRestrictionIfNotNull(criteria, ZONE_ID, zoneId);

        return criteria;
    }

    private void addRestrictionIfNotNull(DetachedCriteria criteria, String propertyName, Object value) {
        if (value != null) {
            criteria.add(Restrictions.eq(propertyName, value));
        }
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
