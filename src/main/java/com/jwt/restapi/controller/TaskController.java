package com.jwt.restapi.controller;

import com.jwt.restapi.dto.TaskDto;
import com.jwt.restapi.entity.Task;
import com.jwt.restapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service) {
        this.taskService = service;
    }

    // CREATE
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createTask(
            @ModelAttribute TaskDto dto,
            @RequestParam(value = "attachment", required = false) MultipartFile file) {
        try {
            Task task = taskService.createTask(dto, file);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // READ ALL
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    // UPDATE
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @ModelAttribute TaskDto dto,
            @RequestParam(value = "attachment", required = false) MultipartFile file) throws IOException {
        Task updated = taskService.updateTask(id, dto, file);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    //GET ALL TASKS WITH PASSED USERID

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

}
