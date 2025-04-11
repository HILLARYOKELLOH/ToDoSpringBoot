package com.jwt.restapi.dto;

import com.jwt.restapi.entity.TaskStatusHistory.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusHistoryDTO {

    private Long taskId;
    private Status status;
    private LocalDateTime changedAt;
}
