package com.hemant.workflow_orchestrator.repository;

import com.hemant.workflow_orchestrator.models.TaskExecutionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskExecutionRepository
        extends JpaRepository<
        TaskExecutionModel,
        Long
        > {
}