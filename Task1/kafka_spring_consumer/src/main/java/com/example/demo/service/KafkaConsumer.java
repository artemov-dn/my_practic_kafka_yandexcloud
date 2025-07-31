package com.example.demo.service;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaConsumer {
    public static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;

    @Value("${bootstrap-servers}")
    String servers;

    @Value("${kafka-topics}")
    String topics;

    @Value("${truststore-location}")
    String truststoreLocation;

    @Value("${truststore-password}")
    String truststorePassword;

    @Value("${kafka-username}")
    String kafkaUsername;

    @Value("${kafka-password}")
    String kafkaPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws Exception {
        // Конфигурация консумера
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-1");        // Уникальный идентификатор группы
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");        // Начало чтения с самого начала
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");           // Автоматический коммит смещений
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        properties.put(SaslConfigs.SASL_JAAS_CONFIG,
                "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                        "username=\"" + kafkaUsername + "\" password=\"" + kafkaPassword + "\";");
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation); // Truststore
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword); // Truststore password
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, ""); // Отключение проверки hostname

        // Создание консьюмера
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        // Подписка на топик
        consumer.subscribe(Arrays.asList(topics.split(",")));
    }

    @Scheduled(fixedDelay = 2000)
    public void pollMessage() {
        if (consumer != null) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));  // Получение сообщений
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> record : records) {
                        LOG.info("Received message to topic '{}': {}", record.topic(), record.value());
                    }
                }
            } catch (RecordDeserializationException e) {
                LOG.error("Error deserializing message!");
            } catch (Exception e) {
                LOG.error("Error get message from kafka!");
            }
        }
    }

}
