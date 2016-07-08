package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.GeographicalZoneRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.exception.GeographicalZoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeographicalZoneServiceImpl extends AbstractService<GeographicalZone> implements GeographicalZoneService {

    @Autowired
    private GeographicalZoneRepository geographicalZoneRepository;


    @Override
    public GeographicalZone save(GeographicalZone entity) {
        throw ApplicationException.getBuilderFor(GeographicalZoneException.class)
                .forReason(GeographicalZoneException.Reason.SAVE_IS_NOT_SUPPORTED)
                .withMessage("save is not supported")
                .build();
    }

    @Override
    public GeographicalZone update(GeographicalZone entity) {
        throw ApplicationException.getBuilderFor(GeographicalZoneException.class)
                .forReason(GeographicalZoneException.Reason.UPDATE_IS_NOT_SUPPORTED)
                .withMessage("update is not supported")
                .build();
    }

    @Override
    Repository<GeographicalZone> getRepository() {
        return geographicalZoneRepository;
    }

    @Override
    Class<GeographicalZone> getType() {
        return GeographicalZone.class;
    }
}
