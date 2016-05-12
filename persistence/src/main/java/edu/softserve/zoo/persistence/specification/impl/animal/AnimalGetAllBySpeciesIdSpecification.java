package edu.softserve.zoo.persistence.specification.impl.animal;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Serhii Alekseichenko
 */
public class AnimalGetAllBySpeciesIdSpecification implements CriterionSpecification<Animal> {
    private static final String SPECIES_ID = Species.class.getSimpleName().toLowerCase() + ".id";
    private int speciesId;

    public AnimalGetAllBySpeciesIdSpecification(int speciesId) {
        this.speciesId = speciesId;
    }

    @Override
    public Class<Animal> getType() {
        return Animal.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.eq(SPECIES_ID, speciesId);
    }
}
