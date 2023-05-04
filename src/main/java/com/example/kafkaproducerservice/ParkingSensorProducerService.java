package com.example.kafkaproducerservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ParkingSensorProducerService {
    private final KafkaTemplate<String, ParkingSensor> kafkaTemplate;
    private boolean isOccupied = false;

    public ParkingSensorProducerService(KafkaTemplate<String, ParkingSensor> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 10000)
    public void sendSensorData() {
        // Создаем объект с данными датчика
        ParkingSensor parkingSensor = new ParkingSensor("sensor-233", isOccupied);
        isOccupied = !isOccupied;

        System.out.println(parkingSensor.toString());
        // Отправляем сообщение в Kafka
        kafkaTemplate.send("parking-sensor-topic", parkingSensor);
    }
}

