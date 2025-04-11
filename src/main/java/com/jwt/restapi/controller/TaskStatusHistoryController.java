package com.jwt.restapi.controller;

import com.jwt.restapi.entity.TaskStatusHistory;
import com.jwt.restapi.service.TaskStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-status-history")
public class TaskStatusHistoryController {

    private final TaskStatusHistoryService service;

    @Autowired
    public TaskStatusHistoryController(TaskStatusHistoryService service) {
        this.service = service;
    }

    // Get all task status histories
    @GetMapping
    public ResponseEntity<List<TaskStatusHistory>> getAllHistories() {
        return ResponseEntity.ok(service.getAllStatusHistories());
    }

    // Get task status history by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskStatusHistory> getHistoryById(@PathVariable Long id) {
        return service.getStatusHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all histories by task ID
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskStatusHistory>> getHistoriesByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(service.getStatusHistoriesByTaskId(taskId));
    }

    // Create a new task status history
    @PostMapping
    public ResponseEntity<TaskStatusHistory> createHistory(@RequestBody TaskStatusHistory history) {
        return ResponseEntity.ok(service.createStatusHistory(history));
    }

    // Update a task status history
    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusHistory> updateHistory(@PathVariable Long id, @RequestBody TaskStatusHistory history) {
        try {
            return ResponseEntity.ok(service.updateStatusHistory(id, history));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a task status history
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        service.deleteStatusHistory(id);
        return ResponseEntity.noContent().build();
    }
}
