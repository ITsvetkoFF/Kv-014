package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.core.model.Animal;
import edu.softserve.zoo.persistence.repository.AnimalRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllByHouseIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllBySpeciesIdSpecification;
import edu.softserve.zoo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link AnimalService} logic for {@link Animal} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class AnimalServiceImpl extends AbstractService<Animal> implements AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Override
    @Transactional
    public List<Animal> getAllByHouseId(Long id) {
        return repository.find(new AnimalGetAllByHouseIdSpecification(id));
    }

    @Override
    @Transactional
    public List<Animal> getAllBySpeciesId(Long id) {
        return repository.find(new AnimalGetAllBySpeciesIdSpecification(id));
    }

    @Override
    Repository<Animal> getRepository() {
        return repository;
    }

    @Override
    Class<Animal> getType() {
        return Animal.class;
    }


}
