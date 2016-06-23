package edu.softserve.zoo.persistence.specification.hibernate.impl.animal;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Animal specification for retrieving animals in specific {@link House}
 *
 * @author Serhii Alekseichenko
 */
public class AnimalGetAllByHouseIdSpecification implements DetachedCriteriaSpecification<Animal> {
    private static final String HOUSE_ID = Specification.getPropertyNameForWhereClause(House.class, "id");
    private static final String SPECIES = "species";
    private final Long houseId;

    /**
     * @param houseId {@link House} identifier to filter animals
     */
    public AnimalGetAllByHouseIdSpecification(Long houseId) {
        this.houseId = houseId;
    }

    @Override
    public DetachedCriteria query() {
        Validator.notNull(houseId, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        return DetachedCriteria.forClass(Animal.class)
                .setFetchMode(SPECIES, FetchMode.JOIN)
                .add(Restrictions.eq(HOUSE_ID, houseId));
    }
}
