package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.ZooZoneService;
import edu.softserve.zoo.service.exception.ZooZoneException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static edu.softserve.zoo.service.exception.ZooZoneException.Reason.*;
import static org.junit.Assert.*;

/**
 * @author Vadym Holub
 */
public class ZooZoneServiceTest extends AbstractServiceTest<ZooZone> {

    private final static Long EXISTENT_ZOO_ZONE_ID = 5L;
    private final static Long ZONE_WITH_HOUSES = 1L;
    private final static Long EXPECTED_ZONE_ID_AFTER_SAVE = 8L;
    @Autowired
    private ZooZoneService service;

    @Test
    public void testFindOne() {
        ZooZone actual = primaryFindOneTest(EXISTENT_ZOO_ZONE_ID);
        assertPrimaryEquality(getValidZooZone(), actual);
    }

    @Test
    public void testUpdate() {
        ZooZone expected = getValidZooZone();
        expected.setName("test");
        expected.setHouseCapacity(201);
        ZooZone actual = service.update(expected);
        assertPrimaryEquality(expected, actual);
    }

    @Test
    public void testUpdate_withReducedHouseCapacity() {
        try {
            ZooZone zone = getValidZooZone();
            zone.setHouseCapacity(100);
            service.update(zone);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(UPDATE_FAILED, ex.getReason());
            assertEquals(CAPACITY_IS_LESS_THAN_CURRENT, ex.getQualificationReason());
        }

    }

    @Test
    public void testUpdate_geoZoneOfZoneWhichDoesNotContainHouses() {
        ZooZone expected = getValidZooZone();
        GeographicalZone newGeographicalZone = new GeographicalZone();
        newGeographicalZone.setId(2L);
        expected.setGeographicalZone(newGeographicalZone);

        ZooZone actual = service.update(expected);

        assertPrimaryEquality(expected, actual);

    }

    @Test
    public void testUpdate_geoZoneOfZoneWhichContainsHouses() {
        try {
            ZooZone zone = getValidZooZone();
            zone.setId(ZONE_WITH_HOUSES);
            GeographicalZone newGeographicalZone = new GeographicalZone();
            newGeographicalZone.setId(2L);
            zone.setGeographicalZone(newGeographicalZone);
            service.update(zone);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(UPDATE_FAILED, ex.getReason());
            assertEquals(GEO_ZONE_CANNOT_BE_UPDATED, ex.getQualificationReason());
        }

    }

    @Test
    public void testUpdate_nonExistentZone() {
        ZooZone zone = getValidZooZone();
        zone.setId(888L);
        try {
            service.update(zone);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(UPDATE_FAILED, ex.getReason());
            assertEquals(NotFoundException.Reason.BY_ID, ex.getQualificationReason());
        }
    }

    @Test
    public void testSave() {
        ZooZone expected = getValidZooZone();
        expected.setId(EXPECTED_ZONE_ID_AFTER_SAVE);
        ZooZone actual = service.save(expected);
        assertPrimaryEquality(expected, actual);
    }

    @Test
    public void testSave_withNonExistentGeoZone() {
        ZooZone zone = getValidZooZone();
        GeographicalZone nonExistentGeoZone = new GeographicalZone();
        nonExistentGeoZone.setId(99L);
        zone.setGeographicalZone(nonExistentGeoZone);
        try {
            service.save(zone);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(CREATE_FAILED, ex.getReason());
            assertEquals(NotFoundException.Reason.BY_ID, ex.getQualificationReason());
        }

    }


    @Test
    public void testDelete_zoneWhichContainsHouses() {
        try {
            service.delete(ZONE_WITH_HOUSES);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(DELETE_FAILED, ex.getReason());
            assertEquals(ZOO_ZONE_CONTAINS_HOUSES, ex.getQualificationReason());
        }
    }

    @Test
    public void testDelete() {
        service.delete(EXISTENT_ZOO_ZONE_ID);
        assertEquals(null, service.getZoneCapacityById(EXISTENT_ZOO_ZONE_ID));
        try {
            service.findOne(EXISTENT_ZOO_ZONE_ID);
            fail();
        } catch (NotFoundException ex) {
            assertEquals(NotFoundException.Reason.BY_ID, ex.getReason());
        }
    }

    @Test
    public void testDelete_withNonExistentZoneId() {
        try {
            service.delete(99L);
            fail();
        } catch (ZooZoneException ex) {
            assertEquals(DELETE_FAILED, ex.getReason());
            assertEquals(NotFoundException.Reason.BY_ID, ex.getQualificationReason());
        }
    }

    private ZooZone getValidZooZone() {
        ZooZone zone = new ZooZone();
        zone.setId(EXISTENT_ZOO_ZONE_ID);
        zone.setName("North America 2");
        zone.setDescription("North America geozone imitation");
        zone.setHouseCapacity(200);
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setId(13L);
        zone.setGeographicalZone(geographicalZone);
        return zone;
    }

    private void assertPrimaryEquality(ZooZone expected, ZooZone actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getHouseCapacity(), actual.getHouseCapacity());
        assertNotNull(actual.getGeographicalZone());
        assertEquals(expected.getGeographicalZone().getId(), actual.getGeographicalZone().getId());
    }

    @Override
    protected Service<ZooZone> getService() {
        return service;
    }
}
