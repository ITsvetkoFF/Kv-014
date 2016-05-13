package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.impl.SQLScalarSpecificationAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.FloatType;
import org.hibernate.type.Type;

import java.util.Collections;
import java.util.List;

/**
 * Specification for getting percentage of fed animals. Result is between 0 and 1.
 * @author Taras Zubrei
 */
public class StatisticsGetFedAnimalsSpecification extends SQLScalarSpecificationAdapter {
    @Override
    public String query() {
        return "SELECT 1 - num/CAST(animals AS FLOAT) as result FROM (SELECT count(*) AS num FROM ZOO.TASKS WHERE TASK_TYPE_ID = 0 AND STATUS_ID IN (1,3)) as Tn, (SELECT count(*) AS animals FROM ZOO.ANIMALS) as a WHERE animals > 0";
    }

    @Override
    public List<ImmutablePair<String, Type>> scalarValues() {
        return Collections.singletonList(new ImmutablePair<>("result", FloatType.INSTANCE));
    }
}
