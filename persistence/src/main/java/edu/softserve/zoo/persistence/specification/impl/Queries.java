package edu.softserve.zoo.persistence.specification.impl;

/**
 * General interface which contains queries strings
 *
 * @author Taras Zubrei
 */
public interface Queries {
    String STAT_TASK_TYPE = "SELECT TASK_TYPE as type, count(*) as num from" +
            " ZOO.TASKS WHERE ASSIGNEE_ID = %d" +
            " GROUP BY type";
    String STAT_TASK_STATUS = "SELECT STATUS as status, count(*) as num from" +
            " ZOO.TASKS WHERE ASSIGNEE_ID = %d" +
            " GROUP BY status";
    String STAT_FED_ANIMALS = "SELECT 1 - num/CAST(animals AS FLOAT) as result FROM" +
            " (SELECT count(*) AS num FROM ZOO.TASKS WHERE TASK_TYPE = 0 AND STATUS IN (1,3)) as Tn," +
            " (SELECT count(*) AS animals FROM ZOO.ANIMALS) as a" +
            " WHERE animals > 0";
    String STAT_GENERAL = "SELECT * FROM" +
            " (SELECT count(*) AS animals FROM ZOO.ANIMALS) as a," +
            " (SELECT count(*) AS houses FROM ZOO.HOUSES) as h," +
            " (SELECT count(*) AS employees FROM ZOO.EMPLOYEES) as e";
}
