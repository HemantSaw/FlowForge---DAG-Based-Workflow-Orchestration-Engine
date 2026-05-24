package com.hemant.workflow_orchestrator.repository;

import com.hemant.workflow_orchestrator.models.TaskModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    // all tasks whose dependency list contains this task
    List<TaskModel> findByDependsOnContaining(TaskModel task);
}