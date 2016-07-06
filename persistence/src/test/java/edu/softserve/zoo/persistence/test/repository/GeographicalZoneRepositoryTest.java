package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetAllSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class GeographicalZoneRepositoryTest extends AbstractRepositoryTest<GeographicalZone> {

    @Autowired
    private Repository<GeographicalZone> repository;

    @Test
    @Transactional
    public void testFindOneById() {
        GeographicalZone expected = getValidGeoZone();
        GeographicalZone actual = findOne(new GetByIdSpecification<>(getType(), expected.getId()), expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void testGetAll() {
        find(new GetAllSpecification<>(getType()), 14);
    }

    private GeographicalZone getValidGeoZone() {
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setId(3L);
        geographicalZone.setRegionName("Antarctica/Southern Ocean");
        return geographicalZone;
    }

    @Override
    protected Class<GeographicalZone> getType() {
        return GeographicalZone.class;
    }

    @Override
    protected Repository<GeographicalZone> getRepository() {
        return repository;
    }
}
