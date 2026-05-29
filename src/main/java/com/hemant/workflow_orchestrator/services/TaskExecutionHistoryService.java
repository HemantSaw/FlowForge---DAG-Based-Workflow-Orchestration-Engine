package com.hemant.workflow_orchestrator.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hemant.workflow_orchestrator.models.TaskExecutionModel;
import com.hemant.workflow_orchestrator.repository.TaskExecutionRepository;

@Service
public class TaskExecutionHistoryService {

    private final TaskExecutionRepository
            taskExecutionRepository;

    public TaskExecutionHistoryService(TaskExecutionRepository taskExecutionRepository) {
        this.taskExecutionRepository =
                taskExecutionRepository;
    }

    public List<TaskExecutionModel>getTaskExecutions(Long workflowExecutionId) {

        return taskExecutionRepository.findByWorkflowExecutionId(workflowExecutionId);
    }
}
