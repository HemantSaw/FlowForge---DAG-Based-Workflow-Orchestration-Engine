package com.hemant.workflow_orchestrator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.workflow_orchestrator.models.WorkflowExecutionModel;
import com.hemant.workflow_orchestrator.services.WorkflowExecutionHistoryService;

@RestController
@RequestMapping("/workflow-executions-history")
public class WorkflowExecutionHistoryController {

    private final WorkflowExecutionHistoryService
            workflowExecutionHistoryService;

    public WorkflowExecutionHistoryController(
            WorkflowExecutionHistoryService
                    workflowExecutionHistoryService
    ) {
        this.workflowExecutionHistoryService =
                workflowExecutionHistoryService;
    }

    @GetMapping("/workflow/{workflowId}")
    public List<WorkflowExecutionModel>
    getWorkflowExecutions(
            @PathVariable Long workflowId
    ) {

        return workflowExecutionHistoryService
                .getWorkflowExecutions(workflowId);
    }
}
