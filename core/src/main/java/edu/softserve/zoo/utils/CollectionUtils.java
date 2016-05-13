package edu.softserve.zoo.utils;

import edu.softserve.zoo.exceptions.ApplicationException;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Taras Zubrei
 */
public class CollectionUtils {
    public static <T> T getSingleItemOrElse(Collection<T> collection, ApplicationException exception, T orElse) {
        if(collection.size() > 1)
            throw exception;
        return collection.stream().findFirst().orElse(orElse);
    }
}
