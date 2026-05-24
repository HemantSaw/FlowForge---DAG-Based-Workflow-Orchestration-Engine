package com.hemant.workflow_orchestrator.services;

import com.hemant.workflow_orchestrator.models.TaskExecutionModel;
import com.hemant.workflow_orchestrator.models.TaskModel;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutionService {

    @Async("workflowExecutor") // beacuse Async void doesn't wait and make execution orchestration incorrect, we use CompletableFuture.
    public CompletableFuture<Void> executeTaskByType(TaskModel task) {

        String taskType = task.getTaskType();

        switch (taskType) {

            case "FETCH_DATA":
                fetchData(task);
                break;

            case "VALIDATE_DATA":
                validateData(task);
                break;

            case "GENERATE_REPORT":
                generateReport(task);
                break;

            case "STORE_ANALYTICS":
                storeAnalytics(task);
                break;
            case "SEND_EMAIL":
                sendEmail(task);
                break;

            default:
                throw new RuntimeException("Unknown task type");
        }
        return CompletableFuture.completedFuture(null);
    }

    private void fetchData(TaskModel task) {

        System.out.println( Thread.currentThread().getName()); //thread logging

        System.out.println("Fetching analytics data...");

        sleep();

        task.setStatus("SUCCESS");

        System.out.println("Data fetched successfully");
    }

    private void validateData(TaskModel task) {

        System.out.println(Thread.currentThread().getName());

        System.out.println("Validating analytics data...");

        sleep();

        task.setStatus("SUCCESS");

        System.out.println("Validation completed");
    }

    private void generateReport(TaskModel task) {

        System.out.println( Thread.currentThread().getName());

        System.out.println("Generating monthly report...");

        sleep();

        task.setStatus("SUCCESS");

        System.out.println("Report generated successfully");
    }

    private void storeAnalytics(TaskModel task) {

        System.out.println( Thread.currentThread().getName());

        System.out.println("Storing analytics data...");

        sleep();

        task.setStatus("SUCCESS");

        System.out.println("Analytics stored successfully");
    }

    private void sendEmail(TaskModel task) {

        System.out.println( Thread.currentThread().getName());

        System.out.println("Sending email notification...");

        sleep();

        task.setStatus("SUCCESS");

        System.out.println("Email notification sent");
    }

    private void sleep() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void retryDelay() {

        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }

    @Async("workflowExecutor")
    public CompletableFuture<Void> executeTaskWithRetry(
            TaskModel task, TaskExecutionModel taskExecution
    ) {

        int retryCount = 0;

        while (retryCount <= task.getMaxRetries()) {

            try {

                System.out.println(
                        "Attempt "
                                + (retryCount + 1)
                                + " for task: "
                                + task.getName()
                );

                executeTaskByType(task);

                return CompletableFuture.completedFuture(
                        null
                );

            } catch (Exception e) {

                retryCount++;

                taskExecution.setRetryCount(
                        retryCount
                );

                System.out.println(
                        "Retry "
                                + retryCount
                                + " failed for task: "
                                + task.getName()
                );

                retryDelay();
            }
        }

        return CompletableFuture.failedFuture(
                new RuntimeException(
                        "Task failed after max retries"
                )
        );
    }
}