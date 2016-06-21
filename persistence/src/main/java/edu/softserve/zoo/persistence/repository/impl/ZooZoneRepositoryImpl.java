package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link ZooZoneRepository} specific for {@link ZooZone} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class ZooZoneRepositoryImpl extends AbstractRepository<ZooZone> implements ZooZoneRepository {
    @Override
    protected Class<ZooZone> getEntityType() {
        return ZooZone.class;
    }
}
