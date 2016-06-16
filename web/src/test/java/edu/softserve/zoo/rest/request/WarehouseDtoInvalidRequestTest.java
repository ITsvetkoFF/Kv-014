package edu.softserve.zoo.rest.request;

import edu.softserve.zoo.controller.rest.Routes;
import edu.softserve.zoo.dto.InvalidRequestDto;
import edu.softserve.zoo.dto.WarehouseDto;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.validation.FieldError;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Locale;

/**
 * This class is used while testing Invalid Request processing. {@link WarehouseDto} was used as an example.
 *
 * @author Andrii Abramov on 6/13/16.
 */

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/web-test-ctx.xml")
@WebAppConfiguration
@ActiveProfiles(AppProfiles.TEST)
public class WarehouseDtoInvalidRequestTest extends AbstractValidationTest<WarehouseDto> {

    private static final long WAREHOUSE_ID = 1L;
    private static final int WRONG_AMOUNT = -850;
    private static final int WRONG_MAX_CAPACITY = -1000;

    /**
     * {@inheritDoc}
     */
    public WarehouseDto getRequestBody() {
        final WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(WAREHOUSE_ID);
        warehouseDto.setAmount(WRONG_AMOUNT);
        warehouseDto.setMaxCapacity(WRONG_MAX_CAPACITY);
        return warehouseDto;
    }


    /**
     * {@inheritDoc}
     */
    public InvalidRequestDto getExpectedInvalidRequestDto(Locale locale) {

        final String wrongAmountFieldName = "amount";
        final String amountMessageKey = "Min.warehouseDto.amount";
        final String amountMessage = getExpectedMessage(amountMessageKey, locale, wrongAmountFieldName, 0);

        final String wrongMaxCapacityFieldName = "maxCapacity";
        final String maxCapacityMessageKey = "Min.warehouseDto.maxCapacity";
        final String maxCapacityMessage = getExpectedMessage(maxCapacityMessageKey, locale, wrongMaxCapacityFieldName, 0);

        final String wrongSupplyFieldName = "supply";
        final String supplyMessageKey = "NotNull.warehouseDto.supply";
        final String supplyMessage = getExpectedMessage(supplyMessageKey, locale, wrongSupplyFieldName);

        final InvalidRequestDto expected = new InvalidRequestDto();
        expected.addFieldError(new FieldError(wrongAmountFieldName, WRONG_AMOUNT, amountMessage));
        expected.addFieldError(new FieldError(wrongMaxCapacityFieldName, WRONG_MAX_CAPACITY, maxCapacityMessage));
        expected.addFieldError(new FieldError(wrongSupplyFieldName, null, supplyMessage));

        return expected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRoute() {
        return Routes.WAREHOUSES + "/1";
    }

}
