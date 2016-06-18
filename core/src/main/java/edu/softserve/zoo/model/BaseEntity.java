package edu.softserve.zoo.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class BaseEntity {

    @Setter
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

}