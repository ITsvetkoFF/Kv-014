package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.AnimalClass;
import edu.softserve.zoo.persistence.repository.AnimalClassRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.AnimalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link AnimalClassService}.
 *
 * @author Bohdan Cherniakh
 */
@Service
public class AnimalClassServiceImpl extends AbstractService<AnimalClass> implements AnimalClassService {

    @Autowired
    private AnimalClassRepository repository;

    @Override
    Repository<AnimalClass> getRepository() {
        return repository;
    }

    @Override
    Class<AnimalClass> getType() {
        return AnimalClass.class;
    }
}
