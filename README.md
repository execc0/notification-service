#  Notification Service

Микросервис отправки email-уведомлений на основе событий, публикуемых [Task Tracker](https://github.com/execc0/task-tracker) через Apache Kafka.

🔗 [Task Tracker (основной API)](https://github.com/execc0/task-tracker)

---

##  Стек технологий

| Слой | Технология |
|---|---|
| Язык | Java 17 |
| Фреймворк | Spring Boot 3.4.1 |
| Сборка | Gradle (Groovy) |
| Очереди сообщений | Apache Kafka (Spring Kafka) |
| Email | Spring Mail + Mailtrap (SMTP) |
| Мониторинг | Spring Boot Actuator |
| Утилиты | Lombok, Jackson |

---

##  Возможности

Сервис не предоставляет публичного REST API и не принимает входящих запросов от клиентов. Единственная точка входа — это потребление сообщений из Kafka.

### Обрабатываемые события

| Топик Kafka | Описание |
|---|---|
| `task-status-changed` | Изменение статуса задачи — уведомление владельцу задачи |
| `user-registered` | Регистрация нового пользователя — приветственное письмо |
| `user-deleted` | Удаление учётной записи — уведомление об удалении |


##  Структура проекта

```
src/main/java/org/example/notificationservice/
├── kafka/      — консьюмеры Kafka, конфигурация retry и DLT
├── model/      — модели событий
├── service/    — формирование и отправка email
└── NotificationServiceApplication
```

---

##  Установка и запуск

### Требования

- Java 17+
- Запущенный брокер Apache Kafka на `localhost:9092`
- SMTP-сервер или sandbox-аккаунт (например, [Mailtrap](https://mailtrap.io))

### Переменные окружения

| Переменная | Описание |
|---|---|
| `MAIL_USERNAME` | Имя пользователя SMTP |
| `MAIL_PASSWORD` | Пароль SMTP |


### Сборка и запуск

```bash
git clone https://github.com/execc0/notification-service.git
cd notification-service
./gradlew clean build -x test
```

Запуск приложения производится через `NotificationServiceApplication`.

### Проверка работоспособности

```
GET /actuator/health
```

---
