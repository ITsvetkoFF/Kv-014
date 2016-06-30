package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import edu.softserve.zoo.persistence.repository.impl.WarehouseRepositoryImpl;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.persistence.test.coverage.annotation.RepositoryTest;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test Class for {@link WarehouseRepositoryImpl}.
 *
 * @author Andrii Abramov on 6/16/16.
 * @see WarehouseRepository
 * @see WarehouseRepositoryImpl
 */
@ActiveProfiles(AppProfiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@RepositoryTest(forRepository = WarehouseRepositoryImpl.class)
public class WarehouseRepositoryTest {

    private static final Long VALID_WAREHOUSE_ID = 1L;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test(expected = ValidationException.class)
    public void testSaveNullArgument() {
        warehouseRepository.save(null);
    }

    @Test(expected = ValidationException.class)
    public void testUpdateNullArgument() {
        warehouseRepository.update(null);
    }

    @Test(expected = ValidationException.class)
    public void testDeleteNullEntityArgument() {
        warehouseRepository.delete(null, Warehouse.class);
    }

    @Test(expected = ValidationException.class)
    public void testDeleteNullTypeArgument() {
        warehouseRepository.delete(VALID_WAREHOUSE_ID, null);
    }

    @Test(expected = ValidationException.class)
    public void testFindOneNullArgument() {
        warehouseRepository.findOne(null);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void testFindAllNullArgument() {
        warehouseRepository.find(null);
    }

    @Test
    @Transactional
    public void testUpdate() {

        final Warehouse expected = getValidWarehouse();

        final Warehouse warehouseToUpdate = getWarehouseFromDataSource();
        assertNotNull(warehouseToUpdate);
        assertNotNull(warehouseToUpdate.getId());

        warehouseToUpdate.setMaxCapacity(expected.getMaxCapacity());
        warehouseToUpdate.setAmount(expected.getAmount());

        warehouseRepository.update(warehouseToUpdate);

        final Warehouse updatedWarehouse = getWarehouseFromDataSource();
        assertNotNull(updatedWarehouse);
        assertNotNull(updatedWarehouse.getId());

        // update success
        assertEquals(expected, updatedWarehouse);
    }

    private Warehouse getWarehouseFromDataSource() {
        final GetByIdSpecification<Warehouse> getWarehouseById =
                new GetByIdSpecification<>(Warehouse.class, VALID_WAREHOUSE_ID);
        return warehouseRepository.findOne(getWarehouseById);
    }

    private Warehouse getValidWarehouse() {
        final Warehouse rightWarehouse = new Warehouse();
        rightWarehouse.setMaxCapacity(1000);
        rightWarehouse.setAmount(500);
        rightWarehouse.setSupply(Warehouse.Supply.FOOD);
        return rightWarehouse;
    }


}
