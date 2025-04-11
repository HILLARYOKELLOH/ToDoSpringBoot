package com.jwt.restapi.dto;

import com.jwt.restapi.entity.Priority;
import com.jwt.restapi.entity.Status;
import com.jwt.restapi.entity.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class TaskDto {

    private User userId;
private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;
    private MultipartFile attachment;

    // Getters and Setters
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public LocalDate getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(LocalDate dueDate) {
//        this.dueDate = dueDate;
//    }
//
//    public Priority getPriority() {
//        return priority;
//    }
//
//    public void setPriority(Priority priority) {
//        this.priority = priority;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    public MultipartFile getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(MultipartFile attachment) {
//        this.attachment = attachment;
//    }
}
