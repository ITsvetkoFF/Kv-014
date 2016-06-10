package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(Warehouse.class)
public class WarehouseDto extends BaseDto {
    @DocsFieldDescription("Supply type")
    private Warehouse.Supply supply;
    @DocsFieldDescription("The amount")
    private Integer amount;
    @DocsFieldDescription("The max capacity")
    private Integer maxCapacity;

    public WarehouseDto() {
    }

    public Warehouse.Supply getSupply() {
        return supply;
    }

    public void setSupply(Warehouse.Supply supply) {
        this.supply = supply;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "WarehouseDto{" +
                "id=" + getId() +
                ", supply=" + supply +
                ", amount=" + amount +
                ", maxCapacity=" + maxCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDto warehouseDto = (WarehouseDto) o;
        return supply == warehouseDto.supply &&
                Objects.equals(amount, warehouseDto.amount) &&
                Objects.equals(maxCapacity, warehouseDto.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supply, amount, maxCapacity);
    }

}
