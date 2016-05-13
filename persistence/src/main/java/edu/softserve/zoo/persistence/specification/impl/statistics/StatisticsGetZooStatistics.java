package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.impl.SQLScalarSpecificationAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.util.Arrays;
import java.util.List;

/**
 * Specification for getting overall zoo statistics
 * @author Taras Zubrei
 */
public class StatisticsGetZooStatistics  extends SQLScalarSpecificationAdapter {
    @Override
    public String query() {
        return "SELECT * FROM" +
                " (SELECT count(*) AS animals FROM ZOO.ANIMALS) as a," +
                " (SELECT count(*) AS houses FROM ZOO.HOUSES) as h," +
                " (SELECT count(*) AS employees FROM ZOO.EMPLOYEES) as e";
    }

    @Override
    public List<ImmutablePair<String, Type>> scalarValues() {
        return Arrays.asList(new ImmutablePair<>("animals", LongType.INSTANCE),
                new ImmutablePair<>("houses", LongType.INSTANCE),
                new ImmutablePair<>("employees", LongType.INSTANCE));
    }
}
