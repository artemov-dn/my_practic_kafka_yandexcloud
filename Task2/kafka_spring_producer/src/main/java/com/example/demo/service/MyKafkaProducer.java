package com.example.demo.service;

import com.example.demo.domain.Student;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Component
public class MyKafkaProducer {
    public static final Logger LOG = LoggerFactory.getLogger(MyKafkaProducer.class);

    @Value("${bootstrap-servers}")
    String servers;

    @Value("${kafka-topic}")
    String topic;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws Exception {
        // Конфигурация продюсера
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Создание продюсера
        try(KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 1; i <= 5; i++) {
                String key = "key-" + UUID.randomUUID();
                Student student = new Student(i, "Иванов Иван Иванович " + i);

                LOG.info("Try send message key={}, Student: {}", key , student);

                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, student.toString());
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        LOG.info("Message sent: partition={}, offset={}", metadata.partition(), metadata.offset());
                    } else {
                        LOG.error("Error while producing: ", exception);
                    }
                });
                Thread.sleep(1000);
            }
            producer.flush();
        } catch (Exception e) {
            LOG.error("Error send message to kafka!");
        }

    }

}
