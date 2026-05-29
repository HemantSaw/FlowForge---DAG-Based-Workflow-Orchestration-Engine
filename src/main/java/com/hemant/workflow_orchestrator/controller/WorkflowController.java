package com.hemant.workflow_orchestrator.controller;

import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.repository.WorkflowRepository;
import com.hemant.workflow_orchestrator.services.WorkflowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public WorkflowModel getWorkflowByUserId(@PathVariable Long userId) {
        return workflowService.getWorkflowByUserId(userId);
    }

    @GetMapping("/get-workflow-by-workflowId/{workflowId}")
    public WorkflowModel getWorkflowBYWorkflowId(@PathVariable Long workflowId) {
        return workflowService.getWorkflowByWorkflowId(workflowId);
    }

    @DeleteMapping("/delete-workflow-by-workflowId/{workflowId}")
    public String deleteWorkflowByWorkflowId(@PathVariable Long workflowId){
        workflowService.deleteWorkflowByWorkflowId(workflowId);
        return "Workflow Deleted Successfully.";
    }   
    
    
}