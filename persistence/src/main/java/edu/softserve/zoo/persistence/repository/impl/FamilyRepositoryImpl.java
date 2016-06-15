package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.Family;
import edu.softserve.zoo.persistence.repository.FamilyRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link FamilyRepository} specific for {@link Family} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class FamilyRepositoryImpl extends AbstractRepository<Family> implements FamilyRepository {
}
