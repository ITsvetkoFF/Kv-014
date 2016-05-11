package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.Type;
import java.util.List;

/**
 * Created by Taras Zubrei on 06.05.2016.
 */
public interface SQLScalarSpecification<T> extends Specification<T> {
    @Override
    String query();
    List<ImmutablePair<String, Type>> scalarValues();
}
