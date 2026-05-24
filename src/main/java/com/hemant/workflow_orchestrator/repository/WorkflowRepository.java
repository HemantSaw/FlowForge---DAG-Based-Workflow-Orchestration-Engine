package com.hemant.workflow_orchestrator.repository;
import com.hemant.workflow_orchestrator.models.WorkflowModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepository extends JpaRepository<WorkflowModel, Long> {

}
