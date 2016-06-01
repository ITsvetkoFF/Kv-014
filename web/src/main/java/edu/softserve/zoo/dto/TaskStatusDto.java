package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;
import edu.softserve.zoo.model.Task;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Taras Zubrei
 */
@IrrespectiveDto
public class TaskStatusDto {
    private String taskStatus;
    private Long count;


    public TaskStatusDto(Task.TaskStatus taskStatus, Long count) {
        this.taskStatus = StringUtils.capitalize(taskStatus.toString().toLowerCase().replace('_', ' '));
        this.count = count;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
        TaskStatusDto that = (TaskStatusDto) o;
        return Objects.equals(taskStatus, that.taskStatus) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskStatus, count);
    }
}
