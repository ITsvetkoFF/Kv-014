package edu.softserve.zoo.persistence.specification.impl.animal;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Serhii Alekseichenko
 */
public class AnimalGetByIdSpecification implements CriterionSpecification<Animal> {
    private int id;

    public AnimalGetByIdSpecification(int id) {
        this.id = id;
    }

    @Override
    public Class<Animal> getType() {
        return Animal.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.idEq(id);
    }
}
