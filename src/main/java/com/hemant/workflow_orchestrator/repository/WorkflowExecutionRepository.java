package com.hemant.workflow_orchestrator.repository;

import com.hemant.workflow_orchestrator.models.WorkflowExecutionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowExecutionRepository
        extends JpaRepository<
        WorkflowExecutionModel,
        Long
        > {
}