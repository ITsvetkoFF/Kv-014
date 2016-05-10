package edu.softserve.zoo.persistence.specification.stubs.generic;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.specification.Specification;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllAnimalsUnsupportedSpecification implements Specification<Animal>{
    @Override
    public Object query() {
        return null;
    }
}
