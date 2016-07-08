package edu.softserve.zoo.persistence.specification.hibernate.impl.zoo_zone;

import edu.softserve.zoo.persistence.specification.hibernate.SqlGetMapSpecification;

/**
 * @author Vadym Holub
 */
public class GetZooZoneCapacityMapSpecification implements SqlGetMapSpecification<Long, Long> {

    @Override
    public String query() {
        return "select z.id, coalesce(count(h.id), 0)  FROM zoo.zoo_zones z\n" +
                " LEFT JOIN zoo.houses h ON z.id = h.zoo_zone_id\n" +
                "GROUP BY z.id ORDER BY z.id";
    }

    @Override
    public Class<Long> getKeyType() {
        return Long.class;
    }

    @Override
    public Class<Long> getValueType() {
        return Long.class;
    }
}
