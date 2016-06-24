package edu.softserve.zoo.persistence.specification.hibernate.impl.species;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * Species specification for retrieving one species with id and animalsPerHouse properties only
 *
 * @author Serhii Alekseichenko
 */
public class SpeciesFindOneWithAnimalsPerHouseSpecification implements DetachedCriteriaSpecification<Species> {
    private final Long speciesId;

    /**
     * @param speciesId {@link Species} identifier
     */
    public SpeciesFindOneWithAnimalsPerHouseSpecification(Long speciesId) {
        Validator.notNull(speciesId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        this.speciesId = speciesId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Species.class)
                .add(Restrictions.idEq(speciesId))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("animalsPerHouse"), "animalsPerHouse"))
                .setResultTransformer(new AliasToBeanResultTransformer(Species.class));

    }
}
