package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ilya Doroshenko
 */
public class HouseFilterSpecification implements DetachedCriteriaSpecification<House> {
    private static final String ZONE_ID_FIELD = "zone.id";
    private static final String MAX_CAPACITY_FIELD = "maxCapacity";

    /* Filtering parameters. Ignored if null */
    private Long zoneId;
    private Integer maxCapacity;

    public HouseFilterSpecification() {
    }

    @Override
    public DetachedCriteria query() {
        DetachedCriteria criteria = DetachedCriteria.forClass(House.class);

        addRestrictionIfNotNull(criteria, MAX_CAPACITY_FIELD, maxCapacity);
        addRestrictionIfNotNull(criteria, ZONE_ID_FIELD, zoneId);

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
