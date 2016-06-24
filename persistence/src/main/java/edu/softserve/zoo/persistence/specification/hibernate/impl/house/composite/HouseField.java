package edu.softserve.zoo.persistence.specification.hibernate.impl.house.composite;

import org.hibernate.criterion.Projection;

/**
 * @author Vadym Holub
 */
public interface HouseField {
    Projection getField();
    String getPropertyName();
}
