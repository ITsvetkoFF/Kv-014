package edu.softserve.zoo.persistence.specification.stubs.generic;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllAnimalsMultiInterface implements Specification<Animal>, HQLSpecification<Animal> {
    private static final String HQL_QUERY = "from Animal";

    @Override
    public String query() {
        return HQL_QUERY;
    }
}
