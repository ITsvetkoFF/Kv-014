package edu.softserve.zoo.model;

import java.util.Objects;

public class Warehouse extends BaseEntity {
    private Supply supply;
    private Integer amount;

    public Warehouse() {
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
        Warehouse warehouse = (Warehouse) o;
        return supply == warehouse.supply &&
                Objects.equals(amount, warehouse.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supply, amount);
    }

    public enum Supply {
        MEDICINE, FOOD
    }
}
