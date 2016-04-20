package edu.softserv.zoo.persistence.specification;

@Hibernate
@EntityType(Object.class)
@Query()
public interface FindAllUsersSpecification<T> extends Specification<T> {
}
