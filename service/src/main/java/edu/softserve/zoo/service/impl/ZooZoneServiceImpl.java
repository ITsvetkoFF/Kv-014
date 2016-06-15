package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.core.model.ZooZone;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.service.ZooZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link ZooZoneService}.
 *
 * @author Vadym Holub
 */
@Service
public class ZooZoneServiceImpl extends AbstractService<ZooZone> implements ZooZoneService {

    @Autowired
    private ZooZoneRepository zooZoneRepository;

    @Override
    Repository<ZooZone> getRepository() {
        return zooZoneRepository;
    }

    @Override
    Class<ZooZone> getType() {
        return ZooZone.class;
    }
}
