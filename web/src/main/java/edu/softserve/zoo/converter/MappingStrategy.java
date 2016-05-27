package edu.softserve.zoo.converter;

/**
 * Interface defines the {@link ModelConverter} processing strategy.
 *
 * @author Serhii Alekseichenko
 */
interface MappingStrategy {

    /**
     * Check is strategy is suitable for mapping.
     *
     * @param object to be inspected
     * @return {@code true} if strategy  is suitable and
     * {@code false} otherwise
     */
    boolean isApplicable(Object object);


    /**
     * Convert to dto strategy method.
     *
     * @param object to be converted
     * @return converted object
     */
    Object convertToDto(Object object);


    /**
     * Convert to entity strategy method.
     *
     * @param object to be converted
     * @return converted object
     */
    Object convertToEntity(Object object);
}
