package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Family;
import edu.softserve.zoo.persistence.repository.FamilyRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link FamilyService}.
 *
 * @author Bohdan Cherniakh
 */
@Service
public class FamilyServiceImpl extends AbstractService<Family> implements FamilyService {

    @Autowired
    private FamilyRepository repository;

    @Override
    Repository<Family> getRepository() {
        return repository;
    }

    @Override
    Class<Family> getType() {
        return Family.class;
    }
}
