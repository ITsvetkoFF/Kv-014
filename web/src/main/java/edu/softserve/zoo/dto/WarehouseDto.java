package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.Warehouse;

import java.util.Objects;

/**
 * DTO (Data transfer Object) for {@link Warehouse} class.
 *
 * @author Andrii Abramov on 11-May-16.
 */
public class WarehouseDto extends BaseDto {

    private Warehouse.Supply supply;
    private Integer amount;

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

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + getId() +
                ", supply=" + supply +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDto warehouse = (WarehouseDto) o;
        return supply == warehouse.supply &&
                Objects.equals(amount, warehouse.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supply, amount);
    }

}
