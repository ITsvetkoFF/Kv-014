package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.zoo_zone.GetZoneWithHouseCapacityById;
import edu.softserve.zoo.service.ZooZoneService;
import edu.softserve.zoo.service.exception.ZooZoneException;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link ZooZoneService}.
 *
 * @author Vadym Holub
 */
@Service
public class ZooZoneServiceImpl extends AbstractService<ZooZone> implements ZooZoneService {

    @Autowired
    private ZooZoneRepository zooZoneRepository;

    @Transactional
    @Override
    public ZooZone update(ZooZone entity) {
        ZooZone targetZone = zooZoneRepository.findOne(new GetZoneWithHouseCapacityById(entity.getId()));
        Validator.isTrue(entity.getHouseCapacity() > targetZone.getHouseCapacity(),
                ApplicationException.getBuilderFor(ZooZoneException.class)
                        .forReason(ZooZoneException.Reason.CAPACITY_IS_LESS_THAN_CURRENT)
                        .withMessage(
                                String.format(
                                        "received capacity (%d) is lesser than current (%d)",
                                        entity.getHouseCapacity(),
                                        targetZone.getHouseCapacity()))
                        .build());

        return super.update(entity);
    }

    @Override
    Repository<ZooZone> getRepository() {
        return zooZoneRepository;
    }

    @Override
    Class<ZooZone> getType() {
        return ZooZone.class;
    }
}
