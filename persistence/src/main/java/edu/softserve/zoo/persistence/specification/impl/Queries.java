package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Task;

import javax.persistence.Table;

/**
 * General interface which contains queries strings
 *
 * @author Taras Zubrei
 */
public interface Queries {
    String STAT_TASK_TYPE = "select TASK_TYPE, count(1) from zoo."+Task.class.getAnnotation(Table.class).name()+" where ASSIGNEE_ID=%d group by TASK_TYPE";
    String STAT_TASK_STATUS = "select STATUS, count(1) from zoo."+Task.class.getAnnotation(Table.class).name()+" where ASSIGNEE_ID=%d group by STATUS";
    String STAT_FED_ANIMALS = "select count(1) from zoo."+Task.class.getAnnotation(Table.class).name()+" where TASK_TYPE = 0 AND STATUS IN (1,3)";
    String COUNT = "select count(1) from zoo.%s";
}
