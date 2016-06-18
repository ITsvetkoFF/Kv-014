package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "families")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Family extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private AnimalClass animalClass;

}
