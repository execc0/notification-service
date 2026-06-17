package org.example.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.Event;
import org.example.notificationservice.model.TaskStatusEvent;
import org.example.notificationservice.model.UserEvent;
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

    public void sendNotificationStatusChange(TaskStatusEvent event) {

        String formattedText = String.format("""
                Здравствуйте!
                Статус вашей задачи #%d был обновлён на %s
                С уважением, Task Tracker""",
                event.getTaskId(), event.getStatus());
        sendEmail(event, "Изменение статуса задачи", formattedText);

    }

    public void sendNotificationUserDelete(UserEvent event) {

        String formattedText = String.format("""
                Здравствуйте, %s!
                Ваша учетная запись "%s" была удалена.
                С уважением, Task Tracker""",
                event.getName(), event.getUsername());
        sendEmail(event, "Удаление учетной записи", formattedText);

    }

    public void sendNotificationUserRegister(UserEvent event) {

        String formattedText = String.format("""
                Здравствуйте, %s!
                Ваша учётная запись "%s" успешно создана.
                С уважением, Task Tracker""",
                event.getName(), event.getUsername());
        sendEmail(event, "Успешная регистрация", formattedText);


    }

    private void sendEmail(Event event, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(mailFrom);
            message.setTo(event.getEmail());
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            log.info("Email отправлен {}", event);

        } catch (Exception e) {
            log.error("Ошибка отправки email: {} event: {}", e.getMessage(), event);
        }

    }


}
