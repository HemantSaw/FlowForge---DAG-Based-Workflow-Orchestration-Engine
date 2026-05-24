package com.hemant.workflow_orchestrator.controller;

import com.hemant.workflow_orchestrator.models.TaskModel;
import com.hemant.workflow_orchestrator.services.TaskService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/workflow/{workflowId}")
    public TaskModel createTask(
            @PathVariable Long workflowId,
            @RequestBody TaskModel task
    ) {
        return taskService.createTask(workflowId, task);
    }

    @GetMapping("/get-all-tasks")
    public List<TaskModel> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/{taskId}/depends-on/{dependencyTaskId}")
    public TaskModel addDependency(
            @PathVariable Long taskId,
            @PathVariable Long dependencyTaskId
    ) {
        return taskService.addDependency(taskId, dependencyTaskId);
    }

    @DeleteMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return "Task Deleted Successfully.";
    }

    @DeleteMapping("/delete-all-tasks")
    public String deleteAllTask(){
        taskService.deleteAllTask();
        return "All Tasks Deleted Successfully.";
    }
}