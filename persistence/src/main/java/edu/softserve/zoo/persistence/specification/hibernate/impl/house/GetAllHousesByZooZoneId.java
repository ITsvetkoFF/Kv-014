package edu.softserve.zoo.persistence.specification.hibernate.impl.house;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.AbstractCompositeSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of {@link Specification} for retrieving houses by specific {@link ZooZone} id
 *
 * @author Vadym Holub
 */
public class GetAllHousesByZooZoneId extends AbstractCompositeSpecification<House> implements DetachedCriteriaSpecification<House> {
    private static final String ZOO_ZONE_ID_FIELD = "zone.id";
    private final Long zooZoneId;

    public GetAllHousesByZooZoneId(Long zooZoneId) {
        Validator.notNull(zooZoneId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        this.zooZoneId = zooZoneId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(House.class)
                .add(Restrictions.eq(ZOO_ZONE_ID_FIELD, zooZoneId));
    }

    @Override
    public Class<House> getEntityType() {
        return House.class;
    }
}
