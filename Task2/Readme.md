## Описание проекта

Проект разворачивает Hadoop и демонстрирует интеграцию Kafka.
Также в проекте есть простые продюсер и консумер для отправки и приема тестовых сообщений.

### Классы и структура:
- `kafka_spring_consumer\src\main\java\com\example\demo\SpringBootApplication.java` – класс для запуска SpringBoot приложения.
- `kafka_spring_consumer\src\main\java\com\example\demo\service\KafkaConsumer.java` – консьюмер для топика.
- `kafka_spring_producer\src\main\java\com\example\demo\SpringBootApplication.java` – класс для запуска SpringBoot приложения.
- `kafka_spring_producer\src\main\java\com\example\demo\service\MyKafkaProducer.java` – продюсер для топика.
- `config` - папка с настройкми Hadoop
- `schema.json` – cхема данных.
- `Producer.log` – лог продюсера.
- `Consumer.log` – лог консумера.


## Инструкция по запуску

1. Для запуска приложения необходимо перейти в папку Task2 и выполнить команду:
    ```
    docker-compose up -d
    ```

2. Сервисы, которые поднимутся:
    - кластер Hadoop: 1 NameNode (порт 9000) и 3 DataNode (порты 9864, 9865, 9866)
    - Брокер Kafka
    - Kafka UI для визуализации сообщений.
	- Producer передающий сообщения в топик Kafka.
	- Consumer передающий сообщения из топика Kafka в Hadoop.

## Проверка работы

### HDFS данные
 Список файлов в HDFS
	```
	docker exec hadoop-namenode hdfs dfs -ls /data
	```

### Содержимое файла
	```
	docker exec hadoop-namenode hdfs dfs -cat /data/message_<uuid>
	```

### Веб-интерфейсы
	Hadoop NameNode: http://localhost:9870
	HDFS Browser: http://localhost:9870/explorer.html#/data

### Логи
- `producer.log` - логи продюсера
- `consumer.log` - логи консьюмера
- `hdfs_dfs.log` - список файлов в HDFS
