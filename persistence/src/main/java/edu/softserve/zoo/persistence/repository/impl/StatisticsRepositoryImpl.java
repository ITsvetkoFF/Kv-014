package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetFedAnimalTasksSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Taras Zubrei
 */
@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Autowired
    PersistenceProvider persistenceProvider;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Long getFedAnimals() {
        return  (Long) persistenceProvider.find(new StatisticsGetFedAnimalTasksSpecification()).get(0);
    }
}
