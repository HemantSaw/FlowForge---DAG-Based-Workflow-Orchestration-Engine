package com.hemant.workflow_orchestrator.controller;

import com.hemant.workflow_orchestrator.services.WorkflowExecutionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/execute")
public class WorkflowExecutionController {

    private final WorkflowExecutionService workflowExecutionService;

    public WorkflowExecutionController(
            WorkflowExecutionService workflowExecutionService
    ) {
        this.workflowExecutionService = workflowExecutionService;
    }

    @PostMapping("/workflow/{workflowId}")
    public String executeWorkflow(@PathVariable Long workflowId) {

        workflowExecutionService.executeWorkflow(workflowId);

        return "Workflow execution started asynchronously..";
    }
}