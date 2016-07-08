package edu.softserve.zoo.service.impl;

import com.google.common.collect.ImmutableSet;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.HouseField;
import edu.softserve.zoo.persistence.specification.hibernate.impl.zoo_zone.GetZoneWithHouseCapacityAndGeoZoneById;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.ZooZoneService;
import edu.softserve.zoo.service.exception.ZooZoneException;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.softserve.zoo.service.exception.ZooZoneException.Reason.*;


/**
 * Implementation of the {@link ZooZoneService}.
 *
 * @author Vadym Holub
 */
@Service
public class ZooZoneServiceImpl extends AbstractService<ZooZone> implements ZooZoneService {

    private static final String ERROR_LOG_TEMPLATE = "An exception occurred during %s operation.";

    @Autowired
    private GeographicalZoneService geographicalZoneService;

    @Autowired
    private ZooZoneRepository zooZoneRepository;

    @Autowired
    private HouseService houseService;

    @Transactional
    @Override
    public ZooZone update(ZooZone entity) {
        try {
            ZooZone targetZone = zooZoneRepository.findOne(new GetZoneWithHouseCapacityAndGeoZoneById(entity.getId()));
            Validator.notNull(targetZone, ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(NotFoundException.Reason.BY_ID)
                    .build());
            Validator.isTrue(entity.getHouseCapacity() >= targetZone.getHouseCapacity(),
                    ApplicationException.getBuilderFor(ZooZoneException.class)
                            .forReason(CAPACITY_IS_LESS_THAN_CURRENT)
                            .withMessage(String.format("received capacity (%d) is lesser than current (%d)",
                                    entity.getHouseCapacity(),
                                    targetZone.getHouseCapacity()))
                            .build());
            if (!targetZone.getGeographicalZone().getId().equals(entity.getGeographicalZone().getId())) {
                List<House> allByZooZoneId = houseService.getAllByZooZoneId(entity.getId(), ImmutableSet.of(HouseField.ID));
                for (House house : allByZooZoneId) {
                    Validator.isTrue(houseService.getHouseCurrentCapacity(house.getId()) > 0,
                            ApplicationException.getBuilderFor(ZooZoneException.class)
                                    .forReason(GEO_ZONE_CANNOT_BE_UPDATED)
                                    .withMessage(String.format("House id - %d has animals", house.getId()))
                                    .build());
                }
            }
            return super.update(entity);
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(ZooZoneException.class)
                    .forReason(UPDATE_FAILED)
                    .causedBy(ex)
                    .withQualificationReason(ex.getReason())
                    .withMessage(String.format(ERROR_LOG_TEMPLATE, "update"))
                    .build();
        }
    }

    @Transactional
    @Override
    public ZooZone save(ZooZone entity) {
        try {
            geographicalZoneService.findOne(entity.getGeographicalZone().getId());
            return super.save(entity);
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(ZooZoneException.class)
                    .forReason(CREATE_FAILED)
                    .withQualificationReason(ex.getReason())
                    .withMessage(String.format(ERROR_LOG_TEMPLATE, "save"))
                    .causedBy(ex)
                    .build();
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            ZooZone targetZone = findOne(id);
            Validator.notNull(targetZone, ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(NotFoundException.Reason.BY_ID)
                    .build());
            Long capacityOfZone = zooZoneRepository.getCapacityByZoneId(id);
            Validator.isTrue(capacityOfZone == 0,
                    ApplicationException.getBuilderFor(ZooZoneException.class)
                            .forReason(ZOO_ZONE_CONTAINS_HOUSES)
                            .withMessage(String.format("ZooZone with id-%d contains houses", id))
                            .build());
            super.delete(id);
            zooZoneRepository.deleteZoneCapacity(id);
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(ZooZoneException.class)
                    .forReason(ZooZoneException.Reason.DELETE_FAILED)
                    .withMessage(String.format(ERROR_LOG_TEMPLATE, "delete"))
                    .withQualificationReason(ex.getReason())
                    .causedBy(ex)
                    .build();
        }

    }

    @Override
    public Long getZoneCapacityById(Long zoneId) {
        return zooZoneRepository.getCapacityByZoneId(zoneId);
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
