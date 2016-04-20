package edu.softserv.zoo.persistence.specification;

@EntityType(Object.class)
public class FindAllUsersSpecification<T> implements Specification<T> {
    @Override
    public Class<T> entityType() {
        return null;
    }

    @Override
    public Object query() {
        return null;
    }
}
