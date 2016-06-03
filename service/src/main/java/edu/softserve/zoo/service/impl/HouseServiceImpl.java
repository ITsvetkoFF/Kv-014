package edu.softserve.zoo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.impl.HouseFilterSpecification;
import edu.softserve.zoo.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link HouseService} logic for {@link House} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class HouseServiceImpl extends AbstractService<House> implements HouseService {

    @Autowired
    private HouseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    Repository<House> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public List<House> find(Map<String, String> filter) {
        HouseFilterSpecification specification = objectMapper.convertValue(filter, HouseFilterSpecification.class);
        return repository.find(specification);
    }
}
