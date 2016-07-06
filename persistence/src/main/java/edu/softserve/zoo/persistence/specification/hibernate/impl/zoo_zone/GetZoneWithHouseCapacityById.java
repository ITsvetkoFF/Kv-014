package edu.softserve.zoo.persistence.specification.hibernate.impl.zoo_zone;

import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * @author Vadym Holub
 */
public class GetZoneWithHouseCapacityById implements DetachedCriteriaSpecification<ZooZone> {

    private static final String ID_FIELD = "id";


    private final Long zoneId;

    public GetZoneWithHouseCapacityById(Long zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(ZooZone.class)
                .add(Restrictions.eq(ID_FIELD, zoneId))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("houseCapacity"), "houseCapacity"))
                .setResultTransformer(new AliasToBeanResultTransformer(ZooZone.class));
    }
}
