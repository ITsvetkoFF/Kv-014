package edu.softserve.zoo.dto;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  * DTO (Data transfer Object) class.
 *
 * @author Julia Avdeionok
 */
public class TaskDto extends BaseDto {
    private EmployeeDto assignee;
    private EmployeeDto assigner;
    private LocalDateTime estimatedStart;
    private LocalDateTime estimatedFinish;
    private LocalDateTime actualStart;
    private LocalDateTime actualFinish;
    private TaskTypeDto taskTypeDto;
    private ZooZoneDto zoneDto;
    private String description;
    private TaskStatusDto statusDto;

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

    public TaskTypeDto getTaskTypeDto() {
        return taskTypeDto;
    }

    public void setTaskTypeDto(TaskTypeDto taskTypeDto) {
        this.taskTypeDto = taskTypeDto;
    }

    public ZooZoneDto getZone() {
        return zoneDto;
    }

    public void setZone(ZooZoneDto zoneDto) {
        this.zoneDto = zoneDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatusDto getStatusDto() {
        return statusDto;
    }

    public void setStatusDto(TaskStatusDto statusDto) {
        this.statusDto = statusDto;
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
                ", taskTypeDto=" + taskTypeDto +
                ", zoneDto=" + zoneDto +
                ", description='" + description + '\'' +
                ", statusDto=" + statusDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto task = (TaskDto) o;
        return Objects.equals(assignee, task.assignee) &&
                Objects.equals(assigner, task.assigner) &&
                Objects.equals(estimatedStart, task.estimatedStart) &&
                Objects.equals(estimatedFinish, task.estimatedFinish) &&
                Objects.equals(actualStart, task.actualStart) &&
                Objects.equals(actualFinish, task.actualFinish) &&
                Objects.equals(taskTypeDto, task.taskTypeDto) &&
                Objects.equals(zoneDto, task.zoneDto) &&
                Objects.equals(description, task.description) &&
                Objects.equals(statusDto, task.statusDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estimatedStart, estimatedFinish, actualStart, actualFinish, taskTypeDto,
                zoneDto, assignee, assigner, description, statusDto);
    }

    public static class TaskTypeDto extends BaseDto {
        private Type type;

        public TaskTypeDto() {
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "TaskType{" +
                    "id=" + getId() +
                    ", type=" + type +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskTypeDto taskType = (TaskTypeDto) o;
            return type == taskType.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }

        public enum Type {
            FEEDING, HEALTH_INSPECTION, GIVE_MEDICINE
        }
    }

    public static class TaskStatusDto extends BaseDto {
        private StatusDto statusDto;

        public TaskStatusDto() {
        }

        public StatusDto getStatusDto() {
            return statusDto;
        }

        public void setStatusDto(StatusDto statusDto) {
            this.statusDto = statusDto;
        }

        @Override
        public String toString() {
            return "TaskStatus{" +
                    "id=" + getId() +
                    ", statusDto=" + statusDto +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskStatusDto that = (TaskStatusDto) o;
            return statusDto == that.statusDto;
        }

        @Override
        public int hashCode() {
            return Objects.hash(statusDto);
        }

        public enum StatusDto {
            ACCOMPLISHED, FAILED, RESCEDULED, IN_PROGRESS
        }
    }

}
