package com.hemant.workflow_orchestrator.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkflowExecutionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private WorkflowModel workflow;

    @OneToMany(
            mappedBy = "workflowExecution",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<TaskExecutionModel> taskExecutions =
            new ArrayList<>();

    public WorkflowExecutionModel() {
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(
            LocalDateTime completedAt
    ) {
        this.completedAt = completedAt;
    }

    public WorkflowModel getWorkflow() {
        return workflow;
    }

    public void setWorkflow(
            WorkflowModel workflow
    ) {
        this.workflow = workflow;
    }

    public List<TaskExecutionModel> getTaskExecutions() {
        return taskExecutions;
    }

    public void setTaskExecutions(
            List<TaskExecutionModel> taskExecutions
    ) {
        this.taskExecutions = taskExecutions;
    }
}