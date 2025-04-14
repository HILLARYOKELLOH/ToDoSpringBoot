package com.jwt.restapi.service;

import com.jwt.restapi.dto.TaskDto;
import com.jwt.restapi.entity.Task;
import com.jwt.restapi.entity.User;
import com.jwt.restapi.repository.TaskRepository;
import com.jwt.restapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Get tasks for a specific user
    public List<Task> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return taskRepository.findByUserId(user);
        }
        return List.of(); // Return empty list if user not found
    }

    // Create a task with optional attachment
    public Task createTask(TaskDto dto, MultipartFile attachmentFile) throws IOException {
        Task task = new Task();
        Object user_object=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println( "Okello" + user_object);
        //User user=userRepository.findByEmail
        //User user=user_object.
        String username;
        if (user_object instanceof UserDetails) {
            username = ((UserDetails) user_object).getUsername();
        } else {
            username = user_object.toString();
        }

        User user = userRepository.findByUsername(username);
        //String email = user.getEmail();

        task.setUserId(user);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        if (attachmentFile != null && !attachmentFile.isEmpty()) {
            task.setAttachmentName(attachmentFile.getOriginalFilename());
            task.setAttachmentType(attachmentFile.getContentType());
            task.setAttachmentData(attachmentFile.getBytes());
        }

        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a task by ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Update task with optional new attachment
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

    // Delete task by ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}