package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.persistence.provider.impl.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_FED_ANIMALS;

/**
 * @author Taras Zubrei
 */
@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Autowired
    JdbcPersistenceProvider<Long> persistenceProvider;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Long getFedAnimals() {
        return persistenceProvider.findOne(STAT_FED_ANIMALS, BigInteger::longValue);
    }
}
