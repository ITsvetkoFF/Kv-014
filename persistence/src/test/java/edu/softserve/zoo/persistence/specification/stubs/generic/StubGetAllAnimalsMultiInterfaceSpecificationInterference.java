package edu.softserve.zoo.persistence.specification.stubs.generic;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.Type;

import java.util.List;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllAnimalsMultiInterfaceSpecificationInterference implements HQLSpecification<Animal>, SQLSpecification<Animal> {
    @Override
    public String query() {
        return "";
    }

    @Override
    public List<ImmutablePair<String, Type>> scalarValues() {
        return null;
    }

    @Override
    public Class<Animal> getType() {
        return Animal.class;
    }
}
