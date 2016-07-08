package edu.softserve.zoo.persistence.specification.hibernate.composite.fields;

import edu.softserve.zoo.model.House;

/**
 * @author Vadym Holub
 */
public enum HouseField implements EntityField<House> {
    ID("id"), NAME("name"), MAX_CAPACITY("maxCapacity"), ZONE("zone");

    private final String name;

    HouseField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


}
