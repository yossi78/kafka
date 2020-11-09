package com.demo.kafka.kafka;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
@Data
public class KafkaSender {

    private  Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${kafka.producer.topic}")
    private String PRODUCER_TOPIC;


    @Autowired
    public KafkaSender(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    public void send(String message){
        logger.info("TOPIC=" + PRODUCER_TOPIC + " ,Produce message to Kafka");
        kafkaTemplate.send(PRODUCER_TOPIC,message);
    }




}
