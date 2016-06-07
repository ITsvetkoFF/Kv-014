package edu.softserve.zoo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigner_id", nullable = false)
    public Employee getAssigner() {
        return assigner;
    }

    public void setAssigner(Employee assigner) {
        this.assigner = assigner;
    }

    @Column(name = "started_estimated", nullable = false)
    public LocalDateTime getEstimatedStart() {
        return estimatedStart;
    }

    public void setEstimatedStart(LocalDateTime estimatedStart) {
        this.estimatedStart = estimatedStart;
    }

    @Column(name = "accomplished_estimated", nullable = false)
    public LocalDateTime getEstimatedFinish() {
        return estimatedFinish;
    }

    public void setEstimatedFinish(LocalDateTime estimatedFinish) {
        this.estimatedFinish = estimatedFinish;
    }

    @Column(name = "started_actual")
    public LocalDateTime getActualStart() {
        return actualStart;
    }

    public void setActualStart(LocalDateTime actualStart) {
        this.actualStart = actualStart;
    }

    @Column(name = "accomplished_actual")
    public LocalDateTime getActualFinish() {
        return actualFinish;
    }

    public void setActualFinish(LocalDateTime actualFinish) {
        this.actualFinish = actualFinish;
    }

    @Enumerated
    @Column(name = "task_type", nullable = false)
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    public ZooZone getZone() {
        return zone;
    }

    public void setZone(ZooZone zone) {
        this.zone = zone;
    }

    @Column(name = "task_comment", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated
    @Column(name = "status")
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


    public enum TaskType {
        FEEDING, HEALTH_INSPECTION, GIVE_MEDICINE
    }

    public enum TaskStatus {
        ACCOMPLISHED, FAILED, RESCHEDULED, IN_PROGRESS
    }
}
