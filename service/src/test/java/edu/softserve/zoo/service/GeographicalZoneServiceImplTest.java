package edu.softserve.zoo.service;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.repository.GeographicalZoneRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.impl.GeographicalZoneGetAllSpecification;
import edu.softserve.zoo.service.impl.GeographicalZoneServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;


import java.util.Collections;


public class GeographicalZoneServiceImplTest extends CrudServiceImplTest<GeographicalZone> {

    @InjectMocks
    private GeographicalZoneService geographicalZoneService = new GeographicalZoneServiceImpl();

    @Mock
    private GeographicalZoneRepository geographicalZoneRepository;

    @Test
    public void testGetAll() throws Exception {
        GeographicalZone expected = getExpected();

        when(geographicalZoneRepository.find(new GeographicalZoneGetAllSpecification())).thenReturn(Collections.singletonList(getActual()));

        Assert.assertEquals(Collections.singletonList(expected), geographicalZoneService.getAll());
    }

    @Override
    public GeographicalZone getExpected() {
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setRegionName("East Pacific");
        return geographicalZone;
    }

    @Override
    public GeographicalZone getActual() {
        GeographicalZone geographicalZone = new GeographicalZone();
        geographicalZone.setRegionName("East Pacific");
        return geographicalZone;
    }

    @Override
    public Repository<GeographicalZone> getRepository() {
        return geographicalZoneRepository;
    }

    @Override
    public Service<GeographicalZone> getService() {
        return geographicalZoneService;
    }


}
