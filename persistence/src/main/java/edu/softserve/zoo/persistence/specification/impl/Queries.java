package edu.softserve.zoo.persistence.specification.impl;

/**
 * General interface which contains queries strings
 *
 * @author Taras Zubrei
 */
public interface Queries {
    String STAT_TASK_TYPE = "select new map(task.taskType, count(task)) from Task task where task.assignee=%d group by task.taskType";
    String STAT_TASK_STATUS = "select new map(task.status, count(task)) from Task task where task.assignee=%d group by task.status";
    String STAT_FED_ANIMALS = "select count(task) from Task task where task.taskType = 0 AND task.status IN (1,3)";
    String COUNT = "select count(entity) from %s entity";
}
