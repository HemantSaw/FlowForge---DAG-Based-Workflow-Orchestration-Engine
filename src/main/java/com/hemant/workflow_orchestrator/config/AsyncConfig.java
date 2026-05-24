package com.hemant.workflow_orchestrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "workflowExecutor")
    public Executor workflowExecutor() {

        ThreadPoolTaskExecutor executor =
                new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4); //4 worker threads can execute 4 tasks simultaneously.

        executor.setMaxPoolSize(4); //maximum thread worker allowed

        executor.setQueueCapacity(100); //if all worker busy, tasks wait in queue

        executor.setThreadNamePrefix("workflow-worker-"); //easy to distinguish for thread logging

        executor.initialize();

        return executor;
    }
}