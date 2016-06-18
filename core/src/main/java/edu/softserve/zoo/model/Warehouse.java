package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "warehouses")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Warehouse extends BaseEntity {

    @Enumerated
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Supply supply;
    @Column(name = "amount", nullable = false)
    private Integer amount;
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    public enum Supply {
        MEDICINE, FOOD
    }

}
