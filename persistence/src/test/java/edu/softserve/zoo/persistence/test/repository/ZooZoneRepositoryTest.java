package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetAllSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ZooZoneRepositoryTest extends AbstractRepositoryTest<ZooZone> {

    private static final Long VALID_ZOO_ZONE_ID = 1L;

    @Autowired
    private Repository<ZooZone> repository;

    @Test
    @Transactional
    public void testFindOneById() {
        ZooZone expected = getValidEntity();
        ZooZone actual = findOne(new GetByIdSpecification<>(getType(), expected.getId()), expected.getId());

        assertPrimaryEquality(expected, actual);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void testFindNullParam() {
        repository.find(null);
    }

    @Test
    @Transactional
    public void testSaveOnValidZone() {
        ZooZone expected = getValidEntity();
        ZooZone savedEntity = save(expected, 8L);

        assertPrimaryEquality(expected, savedEntity);
    }

    @Test
    @Transactional
    public void testUpdatePositive() {
        GetByIdSpecification<ZooZone> specification = new GetByIdSpecification<>(getType(), VALID_ZOO_ZONE_ID);
        ZooZone entityForUpdating = findOne(specification, VALID_ZOO_ZONE_ID);
        entityForUpdating.setName("testName");
        entityForUpdating.setDescription("test");
        entityForUpdating.setHouseCapacity(77);

        ZooZone updatedZone = update(entityForUpdating);

        assertPrimaryEquality(entityForUpdating, updatedZone);
    }

    @Test
    @Transactional
    public void testDelete() {
        super.delete(VALID_ZOO_ZONE_ID);
    }

    @Test
    @Transactional
    public void testZooZoneQuantity() {
        find(new GetAllSpecification<>(getType()), 7);
    }


    private void assertPrimaryEquality(ZooZone expected, ZooZone actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getHouseCapacity(), actual.getHouseCapacity());
        assertNotNull(actual.getGeographicalZone());
        assertEquals(expected.getGeographicalZone().getId(), actual.getGeographicalZone().getId());
    }

    private ZooZone getValidEntity() {
        ZooZone zone = new ZooZone();
        zone.setId(5L);
        zone.setName("North America 2");
        zone.setDescription("North America geozone imitation");
        zone.setHouseCapacity(200);
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setId(13L);
        zone.setGeographicalZone(geographicalZone);
        return zone;
    }


    @Override
    protected Repository<ZooZone> getRepository() {
        return repository;
    }

    @Override
    protected Class<ZooZone> getType() {
        return ZooZone.class;
    }

}
