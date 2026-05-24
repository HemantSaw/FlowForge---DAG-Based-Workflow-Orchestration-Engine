package com.hemant.workflow_orchestrator.controller;

import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.services.WorkflowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/user/{userId}")
    public WorkflowModel createWorkflow(@PathVariable Long userId,@RequestBody WorkflowModel workflow) {
        return workflowService.createWorkflow(userId,workflow);
    }

    @GetMapping
    public List<WorkflowModel> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }

    @GetMapping("/get-workflow-by-userId/{userId}")
    public WorkflowModel getMethodName(@PathVariable Long userId) {
        return workflowService.getWorkflowByUserId(userId);
    }
    
}