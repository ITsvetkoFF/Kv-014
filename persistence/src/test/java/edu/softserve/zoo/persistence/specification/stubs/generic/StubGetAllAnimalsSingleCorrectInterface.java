package edu.softserve.zoo.persistence.specification.stubs.generic;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllAnimalsSingleCorrectInterface implements Cloneable, CriterionSpecification<Animal> {

    @Override
    public Class<Animal> getType() {
        return Animal.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.isNotNull("id");
    }
}
