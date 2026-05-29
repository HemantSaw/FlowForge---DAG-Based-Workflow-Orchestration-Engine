package com.hemant.workflow_orchestrator.services;
import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.repository.WorkflowRepository;

import jakarta.transaction.Transactional;

import com.hemant.workflow_orchestrator.models.UserModel;
import com.hemant.workflow_orchestrator.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkflowService {
    private final UserRepository userRepository;
    private final WorkflowRepository workflowRepository;
    // using dependency injection core concept of spring //
    public WorkflowService(UserRepository userRepository, WorkflowRepository workflowRepository) {
        this.userRepository = userRepository;
        this.workflowRepository = workflowRepository;
    }

    public WorkflowModel createWorkflow(Long userId, WorkflowModel workflow) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        workflow.setUser(user);

        return workflowRepository.save(workflow);
    }

    public WorkflowModel getWorkflowByUserId(Long userId){
        return workflowRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public WorkflowModel getWorkflowByWorkflowId(Long workflowId){
        return workflowRepository.findById(workflowId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + workflowId));
    }
    public List<WorkflowModel> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    @Transactional
    public void deleteWorkflowByWorkflowId(Long workflowId){
        WorkflowModel workflow = workflowRepository.findById(workflowId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + workflowId));
        workflowRepository.delete(workflow);

    }
}
