package org.example.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.TaskStatusEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String mailFrom;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(TaskStatusEvent event) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(mailFrom);
            message.setTo(event.getEmail());
            message.setSubject("Изменение статуса задачи");
            message.setText(buildMessage(event));

            mailSender.send(message);

            log.info("Email отправлен {}", event);

        } catch (Exception e) {
            log.error("Ошибка отправки email: {} для пользователя userId: {}", e.getMessage(), event.getUserId());
        }

    }

    private String buildMessage(TaskStatusEvent event) {
        return String.format("""
                Здравствуйте!
                Статус вашей задачи #%d был обновлён на %s
                С уважением, Task Tracker""", event.getTaskId(), event.getStatus());
    }

}
