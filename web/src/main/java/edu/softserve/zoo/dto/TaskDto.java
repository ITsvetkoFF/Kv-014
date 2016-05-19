package edu.softserve.zoo.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaskDto extends BaseDto {
    private EmployeeDto assignee;
    private EmployeeDto assigner;
    private LocalDateTime estimatedStart;
    private LocalDateTime estimatedFinish;
    private LocalDateTime actualStart;
    private LocalDateTime actualFinish;
    private TaskType taskType;
    private ZooZoneDto zone;
    private String description;
    private TaskStatus status;

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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
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


    public enum TaskType {
        FEEDING, HEALTH_INSPECTION, GIVE_MEDICINE
    }

    public enum TaskStatus {
        ACCOMPLISHED, FAILED, RESCHEDULED, IN_PROGRESS
    }
}
