package edu.softserve.zoo.dto;

import java.util.Objects;

public class WarehouseDto extends BaseDto {
    private Supply supply;
    private Integer amount;

    public WarehouseDto() {
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WarehouseDto{" +
                "id=" + getId() +
                ", supply=" + supply +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDto warehouseDto = (WarehouseDto) o;
        return supply == warehouseDto.supply &&
                Objects.equals(amount, warehouseDto.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supply, amount);
    }

    public enum Supply {
        MEDICINE, FOOD
    }
}
