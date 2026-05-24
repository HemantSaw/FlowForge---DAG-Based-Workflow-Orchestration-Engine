package com.hemant.workflow_orchestrator.services;

import com.hemant.workflow_orchestrator.models.TaskExecutionModel;
import com.hemant.workflow_orchestrator.models.TaskModel;
import com.hemant.workflow_orchestrator.models.WorkflowExecutionModel;
import com.hemant.workflow_orchestrator.models.WorkflowModel;
import com.hemant.workflow_orchestrator.repository.TaskExecutionRepository;
import com.hemant.workflow_orchestrator.repository.TaskRepository;
import com.hemant.workflow_orchestrator.repository.WorkflowExecutionRepository;
import com.hemant.workflow_orchestrator.repository.WorkflowRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WorkflowExecutionService {

    private final WorkflowRepository workflowRepository;
    private final TaskRepository taskRepository;
    private final TaskExecutionService taskExecutionService;
    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final TaskExecutionRepository taskExecutionRepository;

    public WorkflowExecutionService(
            WorkflowRepository workflowRepository,
            TaskRepository taskRepository,
            TaskExecutionService taskExecutionService,
            WorkflowExecutionRepository workflowExecutionRepository,
            TaskExecutionRepository taskExecutionRepository
    ) {
        this.workflowRepository = workflowRepository;
        this.taskRepository = taskRepository;
        this.taskExecutionService = taskExecutionService;
        this.workflowExecutionRepository = workflowExecutionRepository;
        this.taskExecutionRepository = taskExecutionRepository;
    }

    @Async
    public void executeWorkflow(Long workflowId) {

        WorkflowModel workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        System.out.println("==================================");
        System.out.println("STARTING WORKFLOW EXECUTION");
        System.out.println("Workflow: " + workflow.getName());
        System.out.println("==================================");

        workflow.setStatus("RUNNING");
        workflowRepository.save(workflow);
        WorkflowExecutionModel workflowExecution = new WorkflowExecutionModel();

        workflowExecution.setWorkflow(workflow);
        workflowExecution.setStatus("RUNNING");
        workflowExecution.setStartedAt(LocalDateTime.now());
        workflowExecution = workflowExecutionRepository.save(workflowExecution);

        // Implentation of Kahn's Algorithm (BFS in DAG)
        boolean workflowFailed = processWorklowDAG(workflow, workflowExecution);
        
        if(workflowFailed == true){
            workflowExecution.setStatus("FAILED");
            workflow.setStatus("FAILED");
        }
        else{
            workflowExecution.setStatus("SUCCESS");
            workflow.setStatus("SUCCESS");
        }
        workflowExecution.setCompletedAt(LocalDateTime.now());
        workflowExecutionRepository.save(workflowExecution);

        workflowRepository.save(workflow);

        System.out.println("==================================");
        System.out.println("WORKFLOW EXECUTION COMPLETED");
        System.out.println("==================================");
    }

    // function for Kahn's Algorithm.
    private boolean processWorklowDAG(WorkflowModel workflow, WorkflowExecutionModel workflowExecution) {
        boolean workflowFailed = false;

        Map<Long, Integer> inDegreeMap = new HashMap<>();
        Queue<TaskModel> queue = new LinkedList<>();

        int processedTasks = 0;

        for (TaskModel task : workflow.getTasks()) {
            int inDegree = task.getDependsOn().size();
            inDegreeMap.put(task.getId(), inDegree);

            if (inDegree == 0) {
                queue.offer(task);
            }
        }
        // level wise exwecution// all 1st level tasks completes then all 2nd level tasks executes parallely and when all 2nd level completed mpve to all 2rd levl tasks.
        while (!queue.isEmpty()) {

            int currentBatchSize = queue.size(); //current level size.

            List<CompletableFuture<Void>> futures = new ArrayList<>();

            List<TaskModel> currentBatchTasks = new ArrayList<>();

            for (int i = 0; i < currentBatchSize; i++) {

                TaskModel currentTask = queue.poll();

                currentBatchTasks.add(currentTask);
                if (!canExecute(currentTask)) {

                    System.out.println(
                            "Skipping task due to failed dependency: "
                                    + currentTask.getName()
                    );

                    markTaskAsSkipped(currentTask);
                    processedTasks++; //to not break the cycle detection.
                    
                    continue;
                }
                CompletableFuture<Void> future =
                        executeSingleTask(currentTask, workflowExecution);

                futures.add(future);
            }

            
            try{
                CompletableFuture.allOf(
                        futures.toArray(new CompletableFuture[0])
                ).join();  // this join and allOf says that wait until all the tasks of this level is completed then go ahead.
            } catch(Exception e){
                workflowFailed = true;
            }

            processedTasks += currentBatchTasks.size();

            for (TaskModel completedTask : currentBatchTasks) {

                for (TaskModel dependentTask : completedTask.getDependentTasks()) {

                    int updatedInDegree = inDegreeMap.get(dependentTask.getId()) - 1;

                    inDegreeMap.put(dependentTask.getId(),updatedInDegree);

                    if (updatedInDegree == 0) {

                        queue.offer(dependentTask);
                    }
                }
            }
        }
        if(processedTasks != workflow.getTasks().size()){
                throw new RuntimeException(
                    "Cycle detected in workflow DAG"
            );
        }
        return workflowFailed;
    }

    private CompletableFuture<Void> executeSingleTask(TaskModel task, WorkflowExecutionModel workflowExecution) {

        System.out.println("----------------------------------");
        System.out.println("Executing Task: " + task.getName());
        System.out.println("Task Type: " + task.getTaskType());
        System.out.println("----------------------------------");

        task.setStatus("RUNNING");
        taskRepository.save(task);

        TaskExecutionModel taskExecution = new TaskExecutionModel();

        taskExecution.setTask(task);

        taskExecution.setWorkflowExecution(workflowExecution);

        taskExecution.setStatus("RUNNING");

        taskExecution.setRetryCount(0);

        taskExecution.setStartedAt(LocalDateTime.now());

        taskExecutionRepository.save(taskExecution);

        try {

            // CompletableFuture<Void> future =
            //         taskExecutionService.executeTaskByType(task);

            // adding retry task functionality. 
            CompletableFuture<Void> future =
                    taskExecutionService.executeTaskWithRetry(
                            task,
                            taskExecution
                    );

            return future.whenComplete((result, ex) -> {

                if (ex == null) {

                    task.setStatus("SUCCESS");

                    taskRepository.save(task);

                    taskExecution.setStatus("SUCCESS");

                    taskExecution.setCompletedAt(
                            LocalDateTime.now()
                    );

                    taskExecutionRepository.save(
                            taskExecution
                    );

                    System.out.println(
                            "Task Completed: " + task.getName()
                    );

                } else {

                    task.setStatus("FAILED");

                    taskRepository.save(task);

                    taskExecution.setStatus("FAILED");

                    taskExecution.setCompletedAt(
                            LocalDateTime.now()
                    );

                    taskExecutionRepository.save(
                            taskExecution
                    );

                    System.out.println(
                            "Task Failed: " + task.getName()
                    );
                }
            });

        } catch (Exception e) {

            task.setStatus("FAILED");

            taskRepository.save(task);

            taskExecution.setStatus("FAILED");

            taskExecution.setCompletedAt(
                    LocalDateTime.now()
            );

            taskExecutionRepository.save(taskExecution);

            return CompletableFuture.failedFuture(e);
        }
    }

    private boolean canExecute(TaskModel task) {
        for (TaskModel dependency :
                task.getDependsOn()) {

            if (!"SUCCESS".equals(
                    dependency.getStatus()
            )) {

                return false;
            }
        }

        return true;
    }

    private void markTaskAsSkipped(
            TaskModel task
    ) {

        task.setStatus("SKIPPED");

        taskRepository.save(task);

        System.out.println(
                "Task Skipped: "
                        + task.getName()
        );
    }
}