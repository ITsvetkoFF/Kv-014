package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.GetAllByZooZoneIdSpecification;
import edu.softserve.zoo.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link HouseService} logic for {@link House} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class HouseServiceImpl extends AbstractService<House> implements HouseService {

    @Autowired
    private HouseRepository repository;

    @Override
    @Transactional
    public List<House> getAllByZooZoneId(Long id) {
        return repository.find(new GetAllByZooZoneIdSpecification(id));
    }

    @Override
    Repository<House> getRepository() {
        return repository;
    }

    @Override
    Class<House> getType() {
        return House.class;
    }
}
