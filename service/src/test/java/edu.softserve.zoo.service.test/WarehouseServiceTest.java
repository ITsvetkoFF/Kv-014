package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.service.ServiceException;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.service.WarehouseService;
import edu.softserve.zoo.service.config.ServiceConfig;
import edu.softserve.zoo.service.exception.InvalidDataException;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link edu.softserve.zoo.service.impl.WarehouseServiceImpl}
 *
 * @author Andrii Abramov on 6/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
public class WarehouseServiceTest {

    private static final Long VALID_WAREHOUSE_ID = 1L;

    @Autowired
    private WarehouseService warehouseService;

    @Test(expected = ServiceException.class)
    public void testSaveNullArgument() {
        warehouseService.save(null);
    }

    @Test(expected = ServiceException.class)
    public void testUpdateNullArgument() {
        warehouseService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void testDeleteNullArgument() {
        warehouseService.delete(null);
    }

    @Test(expected = ServiceException.class)
    public void testFindOneNullArgument() {
        warehouseService.findOne(null);
    }

    @Test(expected = InvalidDataException.class)
    public void testUpdateWithInvalidData() {
        final Warehouse invalidWarehouse = getInvalidWarehouse();

        final boolean overflow = invalidWarehouse.getAmount() > invalidWarehouse.getMaxCapacity();
        assertTrue("Valid data provided for current test", overflow);

        warehouseService.update(invalidWarehouse);
    }

    @Test(expected = InvalidDataException.class)
    public void testSaveInvalidData() {

        Warehouse invalidWarehouse = getInvalidWarehouse();
        final boolean overflow = invalidWarehouse.getAmount() > invalidWarehouse.getMaxCapacity();
        assertTrue("Valid data provided for current test", overflow);

        warehouseService.update(invalidWarehouse);
    }

    @Test(expected = InvalidDataException.class)
    public void testUpdateInvalidData() {
        final Warehouse warehouseToUpdate = warehouseService.findOne(VALID_WAREHOUSE_ID);
        assertNotNull(warehouseToUpdate);
        assertNotNull(warehouseToUpdate.getId());

        final Warehouse invalidToUpdate = getInvalidWarehouse();
        invalidToUpdate.setId(warehouseToUpdate.getId());

        warehouseService.update(invalidToUpdate);
    }

    @Test
    public void testUpdateValidData() {
        final Warehouse warehouseToUpdate = warehouseService.findOne(VALID_WAREHOUSE_ID);
        assertNotNull(warehouseToUpdate);
        assertNotNull(warehouseToUpdate.getId());

        final Warehouse validToUpdate = getValidWarehouse();
        validToUpdate.setId(warehouseToUpdate.getId());

        final Warehouse updatedWarehouse = warehouseService.update(validToUpdate);
        assertNotNull(updatedWarehouse);
        assertNotNull(updatedWarehouse.getId());
    }

    private Warehouse getInvalidWarehouse() {
        final Warehouse badWarehouse = new Warehouse();
        badWarehouse.setMaxCapacity(1000);
        badWarehouse.setAmount(2000);
        badWarehouse.setSupply(Warehouse.Supply.FOOD);
        return badWarehouse;
    }

    private Warehouse getValidWarehouse() {
        final Warehouse rightWarehouse = new Warehouse();
        rightWarehouse.setMaxCapacity(1000);
        rightWarehouse.setAmount(500);
        rightWarehouse.setSupply(Warehouse.Supply.MEDICINE);
        return rightWarehouse;
    }

}
