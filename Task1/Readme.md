## Описание проекта

Проект разворачивает Kafka кластер в Yandex Cloud.
Также в проекте есть простые продюсер и консумер для отправки и приема тестовых сообщений.

### Классы и структура:
- `kafka_spring_consumer\kafka-creds\kafka.truststore.jks` – truststore для подключения к брокеру.
- `kafka_spring_consumer\src\main\java\com\example\demo\SpringBootApplication.java` – класс для запуска SpringBoot приложения.
- `kafka_spring_consumer\src\main\java\com\example\demo\service\KafkaConsumer.java` – консьюмер для топика.
- `kafka_spring_producer\kafka-creds\kafka.truststore.jks` – truststore для подключения к брокеру.
- `kafka_spring_producer\src\main\java\com\example\demo\SpringBootApplication.java` – класс для запуска SpringBoot приложения.
- `kafka_spring_producer\src\main\java\com\example\demo\service\MyKafkaProducer.java` – продюсер для топика.
- `schema.json` – cхема данных.
- `Producer.log` – лог продюсера.
- `Consumer.log` – лог консумера.


## Описание выполненных шагов

### Шаг 1. Развернул Kafka
- Выбрал минимальные параметры для кластера (s3-c2-m8), так как для учебы требуется передача минимального набора данных
- Включил Реестр схем данных
- Включил Kafka Rest API
- Включил Kafka UI

![kafka1.png](kafka1.png)
- Включил Публичный доступ

![kafka2.png](kafka2.png)
- Создал кластер

![kafka3.png](kafka3.png)

### Шаг 2. Настроил хранение данных
- Создал топик в соответсвии с заданием

![topic.png](topic.png)
- Вывел результат с помощью kcat

![topic_describe.png](topic_describe.png)
- создал пользователя `user-kafka` с необходимыми ролями

![user-kafka.png](user-kafka.png)

### Шаг 3. Настроил Schema Registry
- Создал и сохранил схему в schema.json
- Зарегистрировал схему командой

![kafka_schema_1.png](kafka_schema_1.png)
- Вывел зарегистрированные схемы

![kafka_schema_2.png](kafka_schema_2.png)
- Вывел версию схемы

![kafka_schema_3.png](kafka_schema_3.png)

### Шаг 4. Проверка работы
- Запустило продюсер для отправки сообщений в Kafka (логи в файле Producer.log)

![Producer.png](Producer.png)
- Запустило консьюмер для приема сообщений из Kafka (логи в файле Consumer.log)

![Consumer.png](Consumer.png)
