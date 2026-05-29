package com.hemant.workflow_orchestrator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.workflow_orchestrator.models.TaskExecutionModel;
import com.hemant.workflow_orchestrator.services.TaskExecutionHistoryService;

@RestController
@RequestMapping("/task-executions-history")
public class TaskExecutionHistoryController {

    private final TaskExecutionHistoryService
            taskExecutionHistoryService;

    public TaskExecutionHistoryController(
            TaskExecutionHistoryService
                    taskExecutionHistoryService
    ) {
        this.taskExecutionHistoryService =
                taskExecutionHistoryService;
    }

    @GetMapping(
        "/workflow-execution/{executionId}"
    )
    public List<TaskExecutionModel>
    getTaskExecutions(
            @PathVariable Long executionId
    ) {

        return taskExecutionHistoryService
                .getTaskExecutions(
                        executionId
                );
    }
}
