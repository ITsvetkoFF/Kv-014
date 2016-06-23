package edu.softserve.zoo.persistence.specification.hibernate.impl.house;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import edu.softserve.zoo.util.Validator;

/**
 * House specification for retrieving all houses that applies to a specific species id
 *
 * @author Serhii Alekseichenko
 */
public class HouseGetAllBySpeciesIdSpecification implements HQLSpecification<House> {
    private final Long speciesId;

    /**
     * @param speciesId {@link Species} identifier to filter houses
     */
    public HouseGetAllBySpeciesIdSpecification(Long speciesId) {
        this.speciesId = speciesId;
    }

    @Override
    public String query() {
        Validator.notNull(speciesId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        return String.format("select h from House h join ZooZone z on h.zone.id = z.id " +
                "join GeographicalZone g on z.geographicalZone.id = g.id " +
                "where g in (select geo.id from Species s join s.geographicalZones geo where s.id = %d)", speciesId);
    }
}
