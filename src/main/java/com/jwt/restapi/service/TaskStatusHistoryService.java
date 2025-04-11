package com.jwt.restapi.service;

import com.jwt.restapi.entity.TaskStatusHistory;
import com.jwt.restapi.repository.TaskStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskStatusHistoryService {

    private final TaskStatusHistoryRepository repository;

    @Autowired
    public TaskStatusHistoryService(TaskStatusHistoryRepository repository) {
        this.repository = repository;
    }

    public List<TaskStatusHistory> getAllStatusHistories() {
        return repository.findAll();
    }

    public Optional<TaskStatusHistory> getStatusHistoryById(Long id) {
        return repository.findById(id);
    }

    public List<TaskStatusHistory> getStatusHistoriesByTaskId(Long taskId) {
        return repository.findByTask_Id(taskId);
    }

    public TaskStatusHistory createStatusHistory(TaskStatusHistory statusHistory) {
        if (statusHistory.getChangedAt() == null) {
            statusHistory.setChangedAt(java.time.LocalDateTime.now());
        }
        return repository.save(statusHistory);
    }

    public void deleteStatusHistory(Long id) {
        repository.deleteById(id);
    }

    public TaskStatusHistory updateStatusHistory(Long id, TaskStatusHistory updated) {
        return repository.findById(id).map(existing -> {
            existing.setTask(updated.getTask());
            existing.setStatus(updated.getStatus());
            existing.setChangedAt(updated.getChangedAt() != null ? updated.getChangedAt() : java.time.LocalDateTime.now());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("TaskStatusHistory not found with id " + id));
    }
}
