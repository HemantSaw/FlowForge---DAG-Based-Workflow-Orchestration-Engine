package com.hemant.workflow_orchestrator.services;

import com.hemant.workflow_orchestrator.models.TaskModel;
import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.repository.TaskRepository;
import com.hemant.workflow_orchestrator.repository.WorkflowRepository;

import jakarta.transaction.Transactional;

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

        if(hasPath(dependsOnTask, task)){
            throw new CycleDetectedException("Dependency creates a cycle.");
        }
        task.getDependsOn().add(dependsOnTask);

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        TaskModel task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        // 2. Sever connection with the Parent Workflow
        if (task.getWorkflow() != null) {
            task.setWorkflow(null);
        }

        // 3. Clear this task from all other tasks that depend on it
        // We use the mapped collection directly instead of querying the repository
        List<TaskModel> dependentTasks = task.getDependentTasks();
        if (dependentTasks != null && !dependentTasks.isEmpty()) {
            for (TaskModel dependentTask : dependentTasks) {
                dependentTask.getDependsOn().remove(task);
            }
            // Save updates to clean the inverse side entries out of the join table
            taskRepository.saveAll(dependentTasks);
        }

        // 4. Clear this task's own dependencies (Owning Side)
        task.getDependsOn().clear();

        // 5. Clear its local tracking reference list
        task.getDependentTasks().clear();

        // 6. FORCE Hibernate to sync relationship removals to PostgreSQL right now
        taskRepository.flush();

        // 7. Now that the task is completely isolated, delete it safely
        taskRepository.delete(task);
        
        System.out.println("Task deleted successfully");
    }

    @Transactional
    public void deleteAllTask(){
        taskRepository.deleteAll();
    }

    private boolean hasPath(TaskModel source, TaskModel target) {

        if(source.getId().equals(
                target.getId()
        )) {

            return true;
        }

        for(TaskModel dependency :
                source.getDependsOn()) {

            if(hasPath(
                    dependency,
                    target
            )) {

                return true;
            }
        }

        return false;
    }
}
