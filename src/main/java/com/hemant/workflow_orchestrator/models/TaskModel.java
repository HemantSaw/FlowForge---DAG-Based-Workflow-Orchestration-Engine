package com.hemant.workflow_orchestrator.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String status;

    private String taskType;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    @JsonIgnore
    private WorkflowModel workflow;

    // self-refrencing relationship - using @ManyToOne cause it pointing to the same class type TaskModel.
    // @ManyToOne
    // @JoinColumn(name = "depends_on_task_id")
    // @JsonIgnoreProperties({"workflow", "dependsOn"})
    // private TaskModel dependsOn;

    // dependsOn should me self-refrencing ManyToMany relationShip as one task can depend on multiple tasks.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "task_dependencies",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "depends_on_task_id")
    )
    private List<TaskModel> dependsOn = new ArrayList<>();

    // reverse relationship
    @JsonIgnore
    @ManyToMany(mappedBy = "dependsOn", fetch = FetchType.EAGER)
    private List<TaskModel> dependentTasks = new ArrayList<>();

    private Integer maxRetries = 3;

    public TaskModel() {
    }

    public TaskModel(String name, String status, String taskType) {
        this.name = name;
        this.status = status;
        this.taskType = taskType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public WorkflowModel getWorkflow() {
        return workflow;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setWorkflow(WorkflowModel workflow) {
        this.workflow = workflow;
    }
    
    public List<TaskModel> getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(List<TaskModel> dependsOn) {
        this.dependsOn = dependsOn;
    }

    public List<TaskModel> getDependentTasks() {
        return dependentTasks;
    }

    public void setDependentTasks(List<TaskModel> dependentTasks) {
        this.dependentTasks = dependentTasks;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }
}