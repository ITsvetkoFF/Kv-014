package edu.softserve.zoo.persistence.specification.hibernate.impl.animal;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * Animal specification for retrieving animal by id with birthday, {@link edu.softserve.zoo.model.House} and {@link Species} properties only
 *
 * @author Serhii Alekseichenko
 */
public class AnimalFindOneWithBirthdayHouseAndSpeciesSpecification implements DetachedCriteriaSpecification<Animal> {
    private final Long animalId;

    /**
     * @param animalId {@link Animal} identifier
     */
    public AnimalFindOneWithBirthdayHouseAndSpeciesSpecification(Long animalId) {
        Validator.notNull(animalId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        this.animalId = animalId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Animal.class)
                .add(Restrictions.idEq(animalId))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("house"), "house")
                        .add(Projections.property("birthday"), "birthday")
                        .add(Projections.property("species"), "species"))
                .setResultTransformer(new AliasToBeanResultTransformer(Animal.class));
    }
}
