# TaskManager Spring Boot

Minimal, clean Spring Boot CRUD backend for task lists and tasks.

## Run locally

```bash
./mvnw spring-boot:run
```

Base URL: `http://localhost:8080`

## Run with Docker

```bash
docker compose up --build
```

## API quick test with curl

### 1) Create task list

```bash
curl -s -X POST http://localhost:8080/task-lists \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Uni Work",
    "description": "Semester tasks"
  }'
```

Copy the returned `id` as `TASK_LIST_ID`.

### 2) Get all task lists

```bash
curl -s http://localhost:8080/task-lists
```

### 3) Get one task list

```bash
curl -s http://localhost:8080/task-lists/TASK_LIST_ID
```

### 4) Update task list

```bash
curl -s -X PUT http://localhost:8080/task-lists/TASK_LIST_ID \
  -H "Content-Type: application/json" \
  -d '{
    "id": "TASK_LIST_ID",
    "title": "Uni Work Updated",
    "description": "Updated list description"
  }'
```

### 5) Create task in list

```bash
curl -s -X POST http://localhost:8080/task-lists/TASK_LIST_ID/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Finish assignment",
    "description": "Submit before Friday",
    "priority": "HIGH"
  }'
```

Copy the returned `id` as `TASK_ID`.

### 6) Get tasks in list

```bash
curl -s http://localhost:8080/task-lists/TASK_LIST_ID/tasks
```

### 7) Get one task

```bash
curl -s http://localhost:8080/task-lists/TASK_LIST_ID/tasks/TASK_ID
```

### 8) Update task

```bash
curl -s -X PUT http://localhost:8080/task-lists/TASK_LIST_ID/tasks/TASK_ID \
  -H "Content-Type: application/json" \
  -d '{
    "id": "TASK_ID",
    "title": "Finish assignment (final)",
    "description": "Submitted",
    "priority": "MEDIUM",
    "status": "CLOSED"
  }'
```

### 9) Delete task

```bash
curl -i -X DELETE http://localhost:8080/task-lists/TASK_LIST_ID/tasks/TASK_ID
```

### 10) Delete task list

```bash
curl -i -X DELETE http://localhost:8080/task-lists/TASK_LIST_ID
```

## Notes

- Create endpoints return `201 Created`.
- Delete endpoints return `204 No Content`.
- Validation and not-found errors return structured JSON with HTTP `400`/`404`.
