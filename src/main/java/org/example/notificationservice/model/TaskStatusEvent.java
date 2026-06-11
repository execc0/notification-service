package org.example.notificationservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskStatusEvent {

    private Long taskId;
    private String status;
    private Long userId;
    private String email;

}
