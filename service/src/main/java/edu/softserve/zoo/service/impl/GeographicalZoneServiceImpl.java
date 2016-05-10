package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.GeographicalZoneRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import edu.softserve.zoo.persistence.specification.impl.GeographicalZoneGetAllSpecification;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.exception.NotFoundException;
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

    @Transactional
    public void deleteById(Integer id) {
        Collection<GeographicalZone> geographicalZones = geographicalZoneRepository.find(new HQLSpecification<GeographicalZone>() {
            @Override
            public String query() {
                return "from GeographicalZone g where g.id = " + id;
            }
        });
        if (geographicalZones.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class).forReason(ExceptionReason.NOT_FOUND).build();
        }
        geographicalZoneRepository.delete(geographicalZones.stream().findFirst().get());
    }

    @Override
    Repository<GeographicalZone> getRepository() {
        return geographicalZoneRepository;
    }

}
