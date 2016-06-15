package edu.softserve.zoo.persistence.specification;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.Repository;

/**
 * Interface that defines the specification API.
 * Should provide the data for the {@link Repository}
 *
 * @author Bohdan Cherniakh
 */
public interface Specification<T extends BaseEntity> {

    /**
     * <p>Static util method for creating property name string</p>
     *
     * @param type of entity
     * @return property name with property field separated by period
     * (Example: getPropertyNameForWhereClause(Animal.class, "id") returns "animal.id")
     */
    static String getPropertyNameForWhereClause(Class type, String propertyField) {
        String simpleName = type.getSimpleName();
        char fieldNameArray[] = simpleName.toCharArray();
        fieldNameArray[0] = Character.toLowerCase(fieldNameArray[0]);
        return new String(fieldNameArray) + "." + propertyField;
    }

    /**
     * <p>Defines the API for the query which is used by {@link Repository} and
     * {@link PersistenceProvider}</p>
     *
     * @return the query definition.
     */
    Object query();
}
