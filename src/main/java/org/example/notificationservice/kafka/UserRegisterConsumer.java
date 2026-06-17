package org.example.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.UserEvent;
import org.example.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRegisterConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    public UserRegisterConsumer(ObjectMapper objectMapper, NotificationService notificationService) {
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
    }


    @KafkaListener(topics = "user-registered", groupId = "notification-user-register-group")
    public void onUserRegister(String message) throws JsonProcessingException {

        log.info("Получено сообщение из Kafka: {} ", message);
        UserEvent event = objectMapper.readValue(message, UserEvent.class);
        notificationService.sendNotificationUserRegister(event);


    }
}
