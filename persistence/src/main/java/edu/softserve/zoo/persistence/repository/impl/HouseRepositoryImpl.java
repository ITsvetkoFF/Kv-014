package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link HouseRepository} specific for {@link House} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class HouseRepositoryImpl extends AbstractRepository<House> implements HouseRepository {
    @Override
    protected Class<House> getEntityType() {
        return House.class;
    }
}
