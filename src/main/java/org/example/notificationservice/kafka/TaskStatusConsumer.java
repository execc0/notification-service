package org.example.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.TaskStatusEvent;
import org.example.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskStatusConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public TaskStatusConsumer(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "task-status-changed", groupId = "notification-group")
    public void onStatusChange(String message) throws JsonProcessingException {
        log.info("Получено сообщение из Kafka: {}", message);
        TaskStatusEvent event = objectMapper.readValue(message, TaskStatusEvent.class);
        notificationService.sendNotification(event);
    }

}
