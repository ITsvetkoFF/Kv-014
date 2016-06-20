package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.service.AnimalService;
import edu.softserve.zoo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Taras Zubrei
 */
@Transactional(readOnly = true)
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    StatisticsRepository repo;
    @Autowired
    AnimalService animalService;

    @Override
    public Float getFedAnimalsPercentage() {
        Long animals = animalService.count();
        return animals > 0 ? 1 - repo.getFedAnimals() / Float.valueOf(animals) : .0F;
    }
}
