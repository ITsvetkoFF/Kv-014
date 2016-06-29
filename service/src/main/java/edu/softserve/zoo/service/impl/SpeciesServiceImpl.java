package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.SpeciesRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.species.SpeciesFindOneWithAnimalsPerHouseSpecification;
import edu.softserve.zoo.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SpeciesService} logic for {@link Species} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class SpeciesServiceImpl extends AbstractService<Species> implements SpeciesService {

    @Autowired
    private SpeciesRepository repository;


    @Override
    Repository<Species> getRepository() {
        return repository;
    }

    @Override
    Class<Species> getType() {
        return Species.class;
    }

    @Override
    public Species findOneWithAnimalsPerHouse(Long id) {
        return repository.findOne(new SpeciesFindOneWithAnimalsPerHouseSpecification(id));
    }
}
