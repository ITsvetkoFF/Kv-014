package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.provider.JdbcTemplatePersistenceProvider;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.HouseGetCapacityMapSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

/**
 * <p>Implementation of the {@link HouseRepository} specific for {@link House} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class HouseRepositoryImpl extends AbstractRepository<House> implements HouseRepository {

    @Autowired
    private JdbcTemplatePersistenceProvider persistenceProvider;

    private Map<Long, Long> houseCapacityMap;

    @PostConstruct
    public void initHouseCapacityMap() {
        houseCapacityMap = Collections.synchronizedMap(persistenceProvider.getMap(new HouseGetCapacityMapSpecification()));
    }

    @Override
    public Map<Long, Long> getCapacityMap() {
        return houseCapacityMap;
    }

    @Override
    protected Class<House> getEntityType() {
        return House.class;
    }
}
