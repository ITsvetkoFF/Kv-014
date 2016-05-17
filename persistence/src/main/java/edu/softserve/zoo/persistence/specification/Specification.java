package edu.softserve.zoo.persistence.specification;

/**
 * Interface that defines the specification API.
 * Should provide the data for the {@link edu.softserve.zoo.persistence.repository.Repository#find(Specification)}
 *
 * @author Bohdan Cherniakh
 */
public interface Specification<T> {

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
     * <p>Defines the API for the query which is used by {@link edu.softserve.zoo.persistence.repository.Repository} and
     * {@link edu.softserve.zoo.persistence.provider.PersistenceProvider}</p>
     *
     * @return the query definition.
     */
    Object query();
}
