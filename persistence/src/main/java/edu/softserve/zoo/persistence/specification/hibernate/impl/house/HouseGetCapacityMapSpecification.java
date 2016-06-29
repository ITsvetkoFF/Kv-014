package edu.softserve.zoo.persistence.specification.hibernate.impl.house;

import edu.softserve.zoo.persistence.specification.hibernate.SqlGetMapSpecification;

/**
 * House specification for retrieving capacity map
 *
 * @author Serhii Alekseichenko
 */
public class HouseGetCapacityMapSpecification implements SqlGetMapSpecification<Long,Long> {

    @Override
    public String query() {
        return "select h.id, coalesce(sum (s.animals_per_house), 0) from zoo.houses h " +
                "left join zoo.animals a on h.id = a.house_id " +
                "left join zoo.species s on s.id = a.species_id " +
                "group by h.id order by h.id";
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
