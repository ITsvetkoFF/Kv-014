package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.provider.JdbcTemplatePersistenceProvider;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.zoo_zone.GetZooZoneCapacityMapSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Implementation of the {@link ZooZoneRepository} specific for {@link ZooZone} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class ZooZoneRepositoryImpl extends AbstractRepository<ZooZone> implements ZooZoneRepository {

    @Autowired
    private JdbcTemplatePersistenceProvider persistenceProvider;

    private Map<Long, Long> zoneCapacityMap;

    @PostConstruct
    private void initZooZoneCapacityMap() {
        zoneCapacityMap = new ConcurrentHashMap<>(persistenceProvider.getMap(new GetZooZoneCapacityMapSpecification()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Long> getCapacityForAllZones() {
        return new HashMap<>(zoneCapacityMap);
    }

    @Override
    public Long getCapacityByZoneId(Long id) {
        return zoneCapacityMap.get(id);
    }

    @Override
    public void deleteZoneCapacity(Long zoneId) {
        zoneCapacityMap.remove(zoneId);
    }

    @Override
    protected Class<ZooZone> getEntityType() {
        return ZooZone.class;
    }
}
