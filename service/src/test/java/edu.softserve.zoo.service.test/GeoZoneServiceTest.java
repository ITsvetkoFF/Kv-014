package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.exception.GeographicalZoneException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Vadym Holub
 */

public class GeoZoneServiceTest extends AbstractServiceTest<GeographicalZone> {

    private final static Long EXISTENT_GEO_ZONE_ID = 9L;

    @Autowired
    private GeographicalZoneService service;

    @Test
    public void testFindOne() {
        GeographicalZone expected = getValidGeoZone();
        GeographicalZone actual = primaryFindOneTest(EXISTENT_GEO_ZONE_ID);
        assertEquals(expected.getRegionName(), actual.getRegionName());
    }

    @Test(expected = NotFoundException.class)
    public void testFindOne_withNotExistentId() {
        service.findOne(99L);
    }

    @Test
    public void testFindAll() {
        primaryFindAllTest(14);
    }

    @Test
    public void testSave() {
        try {
            service.save(getValidGeoZone());
            fail();
        } catch (GeographicalZoneException ex) {
            assertEquals(GeographicalZoneException.Reason.SAVE_IS_NOT_SUPPORTED, ex.getReason());
        }
    }

    @Test
    public void testUpdate() {
        try {
            service.update(getValidGeoZone());
            fail();
        } catch (GeographicalZoneException ex) {
            assertEquals(GeographicalZoneException.Reason.UPDATE_IS_NOT_SUPPORTED, ex.getReason());
        }
    }

    private GeographicalZone getValidGeoZone() {
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setId(EXISTENT_GEO_ZONE_ID);
        geographicalZone.setRegionName("Eastern Atlantic Ocean");
        return geographicalZone;
    }

    @Override
    protected Service getService() {
        return service;
    }
}
