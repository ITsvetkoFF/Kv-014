package edu.softserve.zoo.service;

import edu.softserve.zoo.model.GeographicalZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


public class GeographicalZoneServiceImplTest extends CrudServiceImplTest<GeographicalZone> {

    @Autowired
    private GeographicalZoneService geographicalZoneService;

    private GeographicalZone geographicalZone;

    @Before
    public void setUp() throws Exception {
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setId(1);
        geographicalZone.setRegionName("test");
        this.geographicalZone = geographicalZone;
    }

    @Override
    protected Service<GeographicalZone> getService() {
        return geographicalZoneService;
    }

    @Override
    protected GeographicalZone testEntity() {
        return geographicalZone;
    }

    @Test
    public void testGetAll() throws Exception {
        geographicalZone.setId(1);
        geographicalZone.setRegionName("Australia");

        Collection<GeographicalZone> geographicalZones = geographicalZoneService.getAll();
        Assert.assertTrue(geographicalZones.contains(geographicalZone));
    }
}
