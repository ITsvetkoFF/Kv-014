package edu.softserve.zoo.persistence.specification.hibernate.impl.animal;

import edu.softserve.zoo.core.model.Animal;
import edu.softserve.zoo.core.model.House;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Animal specification for retrieving animals in specific {@link House}
 *
 * @author Serhii Alekseichenko
 */
public class AnimalGetAllByHouseIdSpecification implements DetachedCriteriaSpecification<Animal> {
    private static final String HOUSE_ID = Specification.getPropertyNameForWhereClause(House.class, "id");
    private Long houseId;

    /**
     * @param houseId {@link House} identifier to filter animals
     */
    public AnimalGetAllByHouseIdSpecification(Long houseId) {
        this.houseId = houseId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Animal.class)
                .add(Restrictions.eq(HOUSE_ID, houseId));
    }
}
