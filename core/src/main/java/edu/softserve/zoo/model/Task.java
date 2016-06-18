package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Task extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private Employee assignee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigner_id", nullable = false)
    private Employee assigner;
    @Column(name = "started_estimated", nullable = false)
    private LocalDateTime estimatedStart;
    @Column(name = "accomplished_estimated", nullable = false)
    private LocalDateTime estimatedFinish;
    @Column(name = "started_actual")
    private LocalDateTime actualStart;
    @Column(name = "accomplished_actual")
    private LocalDateTime actualFinish;
    @Enumerated
    @Column(name = "task_type", nullable = false)
    private TaskType taskType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private ZooZone zone;
    @Column(name = "task_comment", length = 100)
    private String description;
    @Enumerated
    @Column(name = "status")
    private TaskStatus status;

    public enum TaskType {
        FEEDING, HEALTH_INSPECTION, GIVE_MEDICINE
    }

    public enum TaskStatus {
        ACCOMPLISHED, FAILED, RESCHEDULED, IN_PROGRESS
    }
}
