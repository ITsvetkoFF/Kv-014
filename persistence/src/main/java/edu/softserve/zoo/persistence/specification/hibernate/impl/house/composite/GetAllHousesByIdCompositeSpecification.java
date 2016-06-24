package edu.softserve.zoo.persistence.specification.hibernate.impl.house.composite;

import com.google.common.collect.ImmutableSet;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.Set;

/**
 * @author Vadym Holub
 */
public class GetAllHousesByIdCompositeSpecification implements DetachedCriteriaSpecification<House> {
    private static final String ZOO_ZONE_ID_FIELD = "zone.id";
    private final Long zooZoneId;

    private Set<HouseField> fields = ImmutableSet.of(new HouseIdField(),
            new HouseNameField(),
            new HouseMaxCapacityField(),
            new HouseZoneField());

    public GetAllHousesByIdCompositeSpecification(Long zooZoneId) {
        Validator.notNull(zooZoneId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        this.zooZoneId = zooZoneId;
    }

    @Override
    public DetachedCriteria query() {
        ProjectionList projectionList = Projections.projectionList();
        HouseIdField houseIdField = new HouseIdField();
        if (!fields.contains(houseIdField)) {
            projectionList.add(houseIdField.getField(), houseIdField.getPropertyName());
        }
        fields.forEach(f -> projectionList.add(f.getField(), f.getPropertyName()));
        return DetachedCriteria.forClass(House.class)
                .add(Restrictions.eq(ZOO_ZONE_ID_FIELD, zooZoneId))
                .setProjection(projectionList)
                .setResultTransformer(new AliasToBeanResultTransformer(House.class));
    }

    public void setFields(Set<HouseField> fields) {
        this.fields = fields;
    }
}
