package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;
import edu.softserve.zoo.model.Task;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Taras Zubrei
 */
@IrrespectiveDto
public class TaskTypeDto {
    private String taskType;
    private Long count;

    public TaskTypeDto() {
    }

    public TaskTypeDto(Task.TaskType taskType, Long count) {
        this.taskType = StringUtils.capitalize(taskType.toString().toLowerCase().replace('_', ' '));
        this.count = count;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskTypeDto that = (TaskTypeDto) o;
        return taskType == that.taskType &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskType, count);
    }
}
