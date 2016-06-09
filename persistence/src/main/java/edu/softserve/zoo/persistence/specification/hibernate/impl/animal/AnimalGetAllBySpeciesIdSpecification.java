package edu.softserve.zoo.persistence.specification.hibernate.impl.animal;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Animal specification for retrieving animals of one specific {@link Species}
 *
 * @author Serhii Alekseichenko
 */
public class AnimalGetAllBySpeciesIdSpecification implements DetachedCriteriaSpecification<Animal> {
    private static final String SPECIES_ID = Specification.getPropertyNameForWhereClause(Species.class, "id");
    private Long speciesId;

    /**
     * @param speciesId {@link Species} identifier to filter animals
     */
    public AnimalGetAllBySpeciesIdSpecification(Long speciesId) {
        this.speciesId = speciesId;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Animal.class)
                .add(Restrictions.eq(SPECIES_ID, speciesId));
    }
}
