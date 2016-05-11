package edu.softserve.zoo.service;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Warehouse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Testing {@link WarehouseService}
 *
 * @author Andrii Abramov on 11-May-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-ctx.xml")
public class WarehouseServiceTest {

    @Autowired
    private WarehouseService warehouseService;

    @Test
    public void testGetAllWarehouses() {
        final int actualSize = warehouseService.getAllWarehouses().size();
        final int expectedSize = 2;
        assertEquals("Error with retrieving all warehouses", expectedSize, actualSize);
    }

    @Test
    public void testGetBySupply() {
        final String supplyName = "food";
        final Warehouse warehouse = warehouseService.getBySupply(supplyName);
        assertNotNull(warehouse.getId());
    }

    @Test
    @Rollback
    @Transactional
    public void testUpdate() {
        final String supplyName = "food";
        final Warehouse warehouse = warehouseService.getBySupply(supplyName);
        assertNotNull(warehouse.getId());

        final int expectedAmount = 1000;
        warehouse.setAmount(expectedAmount);
        final Warehouse updatedWarehouse = warehouseService.update(warehouse);
        final int actualAmount = updatedWarehouse.getAmount();
        assertEquals("Error updating amount of warehouse", expectedAmount, actualAmount);
    }


    @Test
    @Rollback
    @Transactional
    public void testDelete() throws ApplicationException {
        final String supplyName = "food";
        final Integer foundId = warehouseService.getBySupply(supplyName).getId();
        assertNotNull(foundId);

        warehouseService.deleteById(foundId);

        try {
            final Warehouse deletionResult = warehouseService.getBySupply(supplyName);
            Assert.fail("Deletion failed with Warehouse,id = " + foundId);
        } catch (ApplicationException ex) {

        }
    }


    @Test(expected = ApplicationException.class)
    public void testUnknownSupplyName() {
        final String supplyName = "wrong_supply_name";
        final Warehouse result = warehouseService.getBySupply(supplyName);
        assertNull(result);
    }


    @Test(expected = ApplicationException.class)
    public void testDeleteByUnknownId() {
        final int unknownId = -1;
        warehouseService.deleteById(unknownId);
    }

    @Test(expected = ApplicationException.class)
    public void testDeleteUnknownEntity() {
        final int unknownId = -1;
        String supplyName = "food";
        final Warehouse unknownEntity = warehouseService.getBySupply(supplyName);
        assertNotNull(unknownEntity);
        unknownEntity.setId(unknownId);
        warehouseService.delete(unknownEntity);
    }


}
