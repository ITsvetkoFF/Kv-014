package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "animals")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Animal extends BaseEntity {

    @Column(name = "nickname", length = 50)
    private String nickname;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "temperature_min", nullable = false)
    private Integer temperatureMin;
    @Column(name = "temperature_max", nullable = false)
    private Integer temperatureMax;
    @Column(name = "animals_per_house", nullable = false)
    private Integer animalsPerHouse;
    @Column(name = "food_consumption", nullable = false)
    private Integer foodConsumption;

}
