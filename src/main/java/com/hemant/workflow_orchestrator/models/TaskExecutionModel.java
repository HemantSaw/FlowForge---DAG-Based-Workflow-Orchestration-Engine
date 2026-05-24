package com.hemant.workflow_orchestrator.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TaskExecutionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Integer retryCount;

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime startedAt;

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskModel task;

    @ManyToOne
    @JoinColumn(name = "workflow_execution_id")
    private WorkflowExecutionModel workflowExecution;

    public TaskExecutionModel() {
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

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(
            LocalDateTime startedAt
    ) {
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

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public WorkflowExecutionModel getWorkflowExecution() {
        return workflowExecution;
    }

    public void setWorkflowExecution(
            WorkflowExecutionModel workflowExecution
    ) {
        this.workflowExecution = workflowExecution;
    }
}