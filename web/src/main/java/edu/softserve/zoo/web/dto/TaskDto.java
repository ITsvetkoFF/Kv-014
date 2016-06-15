package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.Task;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Dto(Task.class)
public class TaskDto extends BaseDto {

    @DocsFieldDescription("The assignee")
    @NotNull
    private EmployeeDto assignee;

    @DocsFieldDescription("The assigner")
    @NotNull
    private EmployeeDto assigner;

    @DocsFieldDescription("The estimated start")
    @NotNull
    private LocalDateTime estimatedStart;

    @DocsFieldDescription("The estimated finish")
    @NotNull
    private LocalDateTime estimatedFinish;

    @DocsFieldDescription("The actual start")
    private LocalDateTime actualStart;

    @DocsFieldDescription("The actual finish")
    private LocalDateTime actualFinish;

    @DocsFieldDescription("The task type")
    @NotNull
    private Task.TaskType taskType;

    @DocsFieldDescription("The zone")
    @NotNull
    private ZooZoneDto zone;

    @DocsFieldDescription("The description")
    @NotNull
    @Length(max = 100)
    private String description;

    @DocsFieldDescription("The status")
    // TODO nullable ?
    private Task.TaskStatus status;

    public TaskDto() {
    }

    public EmployeeDto getAssignee() {
        return assignee;
    }

    public void setAssignee(EmployeeDto assignee) {
        this.assignee = assignee;
    }

    public EmployeeDto getAssigner() {
        return assigner;
    }

    public void setAssigner(EmployeeDto assigner) {
        this.assigner = assigner;
    }

    public LocalDateTime getEstimatedStart() {
        return estimatedStart;
    }

    public void setEstimatedStart(LocalDateTime estimatedStart) {
        this.estimatedStart = estimatedStart;
    }

    public LocalDateTime getEstimatedFinish() {
        return estimatedFinish;
    }

    public void setEstimatedFinish(LocalDateTime estimatedFinish) {
        this.estimatedFinish = estimatedFinish;
    }

    public LocalDateTime getActualStart() {
        return actualStart;
    }

    public void setActualStart(LocalDateTime actualStart) {
        this.actualStart = actualStart;
    }

    public LocalDateTime getActualFinish() {
        return actualFinish;
    }

    public void setActualFinish(LocalDateTime actualFinish) {
        this.actualFinish = actualFinish;
    }

    public Task.TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(Task.TaskType taskType) {
        this.taskType = taskType;
    }

    public ZooZoneDto getZone() {
        return zone;
    }

    public void setZone(ZooZoneDto zone) {
        this.zone = zone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(Task.TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + getId() +
                ", assignee=" + assignee +
                ", assigner=" + assigner +
                ", estimatedStart=" + estimatedStart +
                ", estimatedFinish=" + estimatedFinish +
                ", actualStart=" + actualStart +
                ", actualFinish=" + actualFinish +
                ", taskType=" + taskType +
                ", zone=" + zone +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(assignee, taskDto.assignee) &&
                Objects.equals(assigner, taskDto.assigner) &&
                Objects.equals(estimatedStart, taskDto.estimatedStart) &&
                Objects.equals(estimatedFinish, taskDto.estimatedFinish) &&
                Objects.equals(actualStart, taskDto.actualStart) &&
                Objects.equals(actualFinish, taskDto.actualFinish) &&
                Objects.equals(taskType, taskDto.taskType) &&
                Objects.equals(zone, taskDto.zone) &&
                Objects.equals(description, taskDto.description) &&
                Objects.equals(status, taskDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignee, assigner, estimatedStart, estimatedFinish, actualStart, actualFinish, taskType, zone, description, status);
    }
}
