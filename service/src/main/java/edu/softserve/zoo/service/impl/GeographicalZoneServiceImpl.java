package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.GeographicalZoneRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.GeographicalZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeographicalZoneServiceImpl extends AbstractServiceImpl<GeographicalZone> implements GeographicalZoneService {

    @Autowired
    private GeographicalZoneRepository geographicalZoneRepository;

    @Override
    Repository<GeographicalZone> getRepository() {
        return geographicalZoneRepository;
    }
}
