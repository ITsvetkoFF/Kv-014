package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;

import java.util.List;
import java.util.Objects;

/**
 * @author Taras Zubrei
 */
@IrrespectiveDto
public class TaskStatisticsDto {
    List<TaskStatusDto> taskStatuses;
    List<TaskTypeDto> taskTypes;

    public TaskStatisticsDto() {
    }

    public List<TaskStatusDto> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(List<TaskStatusDto> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public List<TaskTypeDto> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskTypeDto> taskTypes) {
        this.taskTypes = taskTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatisticsDto that = (TaskStatisticsDto) o;
        return Objects.equals(taskStatuses, that.taskStatuses) &&
                Objects.equals(taskTypes, that.taskTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskStatuses, taskTypes);
    }
}
