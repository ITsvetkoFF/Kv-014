package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.Species;
import edu.softserve.zoo.persistence.repository.SpeciesRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link SpeciesRepository} specific for {@link Species} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class SpeciesRepositoryImpl extends AbstractRepository<Species> implements SpeciesRepository {
}
