package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.GeographicalZoneRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.impl.GeographicalZoneGetAllSpecification;
import edu.softserve.zoo.service.GeographicalZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
public class GeographicalZoneServiceImpl extends ServiceImpl<GeographicalZone> implements GeographicalZoneService {

    @Autowired
    private GeographicalZoneRepository geographicalZoneRepository;

    @Transactional
    @Override
    public Collection<GeographicalZone> getAll() {
        return geographicalZoneRepository.find(new GeographicalZoneGetAllSpecification());
    }

    @Override
    Repository<GeographicalZone> getRepository() {
        return geographicalZoneRepository;
    }
}
