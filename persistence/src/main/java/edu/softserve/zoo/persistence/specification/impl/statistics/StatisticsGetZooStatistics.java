package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.SQLScalarSpecification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Taras on 12.05.2016.
 */
public class StatisticsGetZooStatistics implements SQLScalarSpecification {
    @Override
    public String query() {
        return "SELECT * FROM" +
                " (SELECT count(*) AS animals FROM ZOO.ANIMALS) as a," +
                " (SELECT count(*) AS houses FROM ZOO.HOUSES) as h," +
                " (SELECT count(*) AS employees FROM ZOO.EMPLOYEES) as e";
    }

    @Override
    public List<ImmutablePair<String, Type>> scalarValues() {
        return Arrays.asList(new ImmutablePair<>("animals", IntegerType.INSTANCE),
                new ImmutablePair<>("houses", IntegerType.INSTANCE),
                new ImmutablePair<>("employees", IntegerType.INSTANCE));
    }
}
