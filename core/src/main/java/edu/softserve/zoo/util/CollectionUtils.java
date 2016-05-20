package edu.softserve.zoo.util;

import edu.softserve.zoo.exceptions.ApplicationException;

import java.util.Collection;

/**
 * <p>This utility class helps to work with collections</p>
 *
 * @author Taras Zubrei
 */
public class CollectionUtils {
    /**
     * <p>Get single element of collection or orElse if it's not present. If size of collection
     * is {@code > 1} throwing the defined {@link ApplicationException} subtype.</p>
     *
     * @param collection      the collection to work with
     * @param exception an exception that will be thrown if size of collection is {@code > 1}
     * @param orElse    object which will be returned if collection is empty
     * @param <T>       the element type.
     */
    public static <T> T getSingleItemOrElse(Collection<T> collection, ApplicationException exception, T orElse) {
        if(collection.size() > 1)
            throw exception;
        return collection.stream().findFirst().orElse(orElse);
    }
}
