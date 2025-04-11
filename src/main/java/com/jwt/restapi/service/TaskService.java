package com.jwt.restapi.service;

import com.jwt.restapi.dto.TaskDto;
import com.jwt.restapi.entity.Task;
import com.jwt.restapi.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    // CREATE task with attachment
    public Task createTask(TaskDto dto, MultipartFile attachmentFile) throws IOException {
        Task task = new Task();

        task.setUserId(dto.getUserId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        // Handle attachment if provided
        if (attachmentFile != null && !attachmentFile.isEmpty()) {
            task.setAttachmentName(attachmentFile.getOriginalFilename());
            task.setAttachmentType(attachmentFile.getContentType());
            task.setAttachmentData(attachmentFile.getBytes());
        }

        return taskRepository.save(task);
    }

    // GET all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // GET task by id
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // UPDATE task with new data and optional new attachment
    public Task updateTask(Long id, TaskDto dto, MultipartFile attachmentFile) throws IOException {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            existingTask.setUserId(dto.getUserId());
            existingTask.setTitle(dto.getTitle());
            existingTask.setDescription(dto.getDescription());
            existingTask.setDueDate(dto.getDueDate());
            existingTask.setPriority(dto.getPriority());
            existingTask.setStatus(dto.getStatus());

            if (attachmentFile != null && !attachmentFile.isEmpty()) {
                existingTask.setAttachmentName(attachmentFile.getOriginalFilename());
                existingTask.setAttachmentType(attachmentFile.getContentType());
                existingTask.setAttachmentData(attachmentFile.getBytes());
            }

            return taskRepository.save(existingTask);
        }

        return null; // Task not found
    }

    // DELETE task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
