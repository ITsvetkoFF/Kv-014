package edu.softserve.zoo.model;

import java.util.Map;
import java.util.Objects;

/**
 * @author Taras Zubrei
 */
public class TaskStatistics {
    private Map<Task.TaskType, Long> taskTypes;
    private Map<Task.TaskStatus, Long> taskStatuses;

    public TaskStatistics() {
    }

    public Map<Task.TaskType, Long> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(Map<Task.TaskType, Long> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public Map<Task.TaskStatus, Long> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(Map<Task.TaskStatus, Long> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatistics that = (TaskStatistics) o;
        return Objects.equals(taskTypes, that.taskTypes) &&
                Objects.equals(taskStatuses, that.taskStatuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskTypes, taskStatuses);
    }
}
