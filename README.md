# Workflow Orchestration Engine

A DAG-based workflow orchestration engine built using Spring Boot and PostgreSQL that supports asynchronous and parallel task execution with dependency-aware scheduling, retry mechanisms, and failure propagation.

## Live Demo

Frontend - https://flow-forge-dag-based-workflow-orche.vercel.app

Backend - https://flowforge-workflow-orchestrator-engine.onrender.com

## Features

- DAG-based workflow execution
- Dependency-aware task scheduling
- Parallel task execution using worker threads
- Asynchronous workflow processing
- Retry mechanism with failure handling
- Failure propagation and skipped task handling
- Workflow and task execution tracking
- Cycle detection using Topological Sorting
- Execution audit history
- Layered backend architecture using SOLID principles

---

## Tech Stack

- Java
- Spring Boot
- PostgreSQL
- Spring Data JPA / Hibernate
- CompletableFuture
- ThreadPoolTaskExecutor
- Maven

---

## Core Algorithms & Concepts

### Kahn's Algorithm (Topological Sorting)
Used for dependency-aware workflow scheduling.

Ensures:

- Correct task ordering
- DAG traversal
- Parallel level execution
- Cycle detection

---

## System Design Concepts Used

- DAG (Directed Acyclic Graph) based scheduling
- Kahn’s Algorithm (Topological Sorting)
- BFS Graph Traversal
- Parallel task orchestration
- Worker thread pool execution
- Retry and fault-tolerant execution pipelines
- Failure propagation handling
- Immutable execution audit tracking
- Layered Architecture
- Repository Pattern
- Dependency Injection

---

## Architecture

```text
Controller
    ↓
WorkflowExecutionService
    ↓
TaskExecutionService
    ↓
Repositories
    ↓
PostgreSQL
```

---

## Workflow Execution Flow

```text
Workflow
    ↓
Topological Sorting
    ↓
Level-wise DAG Scheduling
    ↓
Parallel Task Execution
    ↓
Failure Handling & Retry
    ↓
Execution Tracking
```

---

## Parallel DAG Execution Example

```text
          FETCH_DATA
               ↓
         VALIDATE_DATA
         ↙          ↘
GENERATE_REPORT   STORE_ANALYTICS
         ↘          ↙
          SEND_EMAIL
```

- `GENERATE_REPORT` and `STORE_ANALYTICS` execute in parallel
- `SEND_EMAIL` waits for both tasks to complete

---

## Retry & Failure Propagation

- Failed tasks are retried automatically
- Downstream dependent tasks are skipped if dependency execution fails
- Workflow status reflects aggregate execution result

---

## Database Design

### Core Tables

- workflow_model
- task_model
- workflow_execution_model
- task_execution_model
- task_dependencies

---

## How to Run

### Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/workflow-orchestration-engine.git
```

### Configure PostgreSQL

Create database:

```sql
CREATE DATABASE workflow_orchestrator;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/workflow_orchestrator
spring.datasource.username=postgres
spring.datasource.password=
```

### Run Application

Backend:

```bash
./mvnw spring-boot:run
```

Frontend:

```
cd frontend
npm install
npm run dev
```

### Environment Variables

Backend:

```
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
```

Frontend:

```
VITE_API_URL=
```

---

## Deployment

### Backend

- Render
- Docker

### Frontend

- Vercel

### Database

- Neon PostgreSQL

---

## Future Improvements

- Redis-based distributed task queue
- Multi-node worker coordination
- WebSocket live workflow monitoring
- JWT authentication
- Workflow visualization dashboard
- Distributed worker execution
- Kubernetes deployment

---

## Key Learnings

- Graph algorithms in backend systems
- Parallel workflow orchestration
- Concurrent task execution
- Fault-tolerant backend design
- Workflow scheduling architecture
- Async programming using CompletableFuture
- Database concurrency handling
- Clean backend architecture using SOLID principles

---

## Author

Hemant Saw
