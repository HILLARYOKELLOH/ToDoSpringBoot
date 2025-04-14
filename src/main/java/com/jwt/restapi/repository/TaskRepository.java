package com.jwt.restapi.repository;

import com.jwt.restapi.entity.Task;
import com.jwt.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(User user);
}

