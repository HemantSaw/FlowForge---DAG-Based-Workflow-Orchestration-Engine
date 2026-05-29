package com.hemant.workflow_orchestrator.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hemant.workflow_orchestrator.models.WorkflowExecutionModel;
import com.hemant.workflow_orchestrator.repository.WorkflowExecutionRepository;

@Service
public class WorkflowExecutionHistoryService {

    private final WorkflowExecutionRepository
            workflowExecutionRepository;

    public WorkflowExecutionHistoryService(
            WorkflowExecutionRepository workflowExecutionRepository
    ) {
        this.workflowExecutionRepository =
                workflowExecutionRepository;
    }

    public List<WorkflowExecutionModel>
    getWorkflowExecutions(
            Long workflowId
    ) {

        return workflowExecutionRepository
                .findByWorkflowIdOrderByStartedAtDesc(
                        workflowId
                );
    }
}
