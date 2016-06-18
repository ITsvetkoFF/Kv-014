package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "classes")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class AnimalClass extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
