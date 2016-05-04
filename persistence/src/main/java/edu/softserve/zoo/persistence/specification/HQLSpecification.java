package edu.softserve.zoo.persistence.specification;

/**
 * @author Bohdan Cherniakh
 */
public interface HQLSpecification<T> extends Specification<T> {

    @Override
    String query();
}
