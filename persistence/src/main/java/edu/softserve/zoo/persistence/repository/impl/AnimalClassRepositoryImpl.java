package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.AnimalClass;
import edu.softserve.zoo.persistence.repository.AnimalClassRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link AnimalClassRepository} specific for {@link AnimalClass} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class AnimalClassRepositoryImpl extends AbstractRepository<AnimalClass> implements AnimalClassRepository {
}
