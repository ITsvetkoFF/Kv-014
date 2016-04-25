package edu.softserve.zoo.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task extends BaseEntity {
    private Employee assignee;
    private Employee assigner;
    private LocalDateTime estimatedStart;
    private LocalDateTime estimatedFinish;
    private LocalDateTime actualStart;
    private LocalDateTime actualFinish;
    private TaskType taskType;
    private ZooZone zone;
    private String description;
    private TaskStatus status;

    public Task() {
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    public Employee getAssigner() {
        return assigner;
    }

    public void setAssigner(Employee assigner) {
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

    public ZooZone getZone() {
        return zone;
    }

    public void setZone(ZooZone zone) {
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
        return "Task{" +
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
        Task task = (Task) o;
        return Objects.equals(assignee, task.assignee) &&
                Objects.equals(assigner, task.assigner) &&
                Objects.equals(estimatedStart, task.estimatedStart) &&
                Objects.equals(estimatedFinish, task.estimatedFinish) &&
                Objects.equals(actualStart, task.actualStart) &&
                Objects.equals(actualFinish, task.actualFinish) &&
                Objects.equals(taskType, task.taskType) &&
                Objects.equals(zone, task.zone) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignee, assigner, estimatedStart, estimatedFinish, actualStart, actualFinish, taskType, zone, description, status);
    }


    public static class TaskType extends BaseEntity {
        private Type type;

        public TaskType() {
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
            TaskType taskType = (TaskType) o;
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

    public static class TaskStatus extends BaseEntity {
        private Status status;

        public TaskStatus() {
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "TaskStatus{" +
                    "id=" + getId() +
                    ", status=" + status +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskStatus that = (TaskStatus) o;
            return status == that.status;
        }

        @Override
        public int hashCode() {
            return Objects.hash(status);
        }

        public enum Status {
            ACCOMPLISHED, FAILED, RESCEDULED, IN_PROGRESS
        }
    }
}
