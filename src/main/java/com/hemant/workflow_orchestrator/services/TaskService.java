package com.hemant.workflow_orchestrator.services;

import com.hemant.workflow_orchestrator.models.TaskModel;
import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.repository.TaskRepository;
import com.hemant.workflow_orchestrator.repository.WorkflowRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final WorkflowRepository workflowRepository;

    public TaskService(TaskRepository taskRepository, WorkflowRepository workflowRepository){
        this.taskRepository = taskRepository;
        this.workflowRepository = workflowRepository;
    }

    // create task service
    public TaskModel createTask(Long workflowId, TaskModel task){
        WorkflowModel workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        task.setWorkflow(workflow);
        return taskRepository.save(task);
    }

    // get all tasks service
    public List<TaskModel> getAllTasks(){
        return taskRepository.findAll();
    }

    // add dependency of the task with other task service
    public TaskModel addDependency(Long taskId, Long dependencyTaskId) {

        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskModel dependsOnTask = taskRepository.findById(dependencyTaskId)
                .orElseThrow(() -> new RuntimeException("Dependency task not found"));

        task.getDependsOn().add(dependsOnTask);

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        TaskModel task = taskRepository.findById(taskId)
            .orElseThrow(()-> new RuntimeException("Task not found"));
        List<TaskModel> dependentTasks = taskRepository.findByDependsOnContaining(task);

        List<TaskModel> updatedDependentTasks = new ArrayList<>();
        for (TaskModel dependentTask : dependentTasks) {

            dependentTask.getDependsOn().remove(task);
            updatedDependentTasks.add(dependentTask);
            // taskRepository.save(dependentTask);
        }
        taskRepository.saveAll(updatedDependentTasks);

        taskRepository.delete(task);
        System.out.println("Task deleted successfully");
    }

    public void deleteAllTask(){
        taskRepository.deleteAll();
    }
}
