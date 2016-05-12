package edu.softserve.zoo.persistence.specification.impl.animal;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Serhii Alekseichenko
 */
public class AnimalGetAllByHouseIdSpecification implements CriterionSpecification<Animal> {
    private static final String HOUSE_ID = House.class.getSimpleName().toLowerCase() + ".id";
    private int houseId;

    public AnimalGetAllByHouseIdSpecification(int houseId) {
        this.houseId = houseId;
    }

    @Override
    public Class<Animal> getType() {
        return Animal.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.eq(HOUSE_ID, houseId);
    }
}
