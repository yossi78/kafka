package com.demo.kafka.kafka;
import com.demo.kafka.utils.XmlUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory;
    private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    private final Integer RETRY_COUNTER =3;


    @Value("${kafka.consumer.topic}")
    private String topic;


    @Autowired
    public KafkaConsumer(ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory) {
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
        consumer=(org.apache.kafka.clients.consumer.KafkaConsumer)kafkaListenerContainerFactory.getConsumerFactory().createConsumer();
        System.out.println(consumer.groupMetadata());
    }


    @PostConstruct
    public void postInit(){
        List<String> topicList = new ArrayList<>();
        topicList.add(topic);
        consumer.subscribe(topicList);
    }


    public ConsumerRecords<String, String> consumeMessages(){
        ConsumerRecords<String, String> records=null;
        for(int i=0;i<RETRY_COUNTER;i++) {
            records = consumer.poll(Duration.ofSeconds(1));
            if(!records.isEmpty()){
                break;
            }
            logger.info("TRY TO CONSUME AGAIN");
        }
        consumer.commitSync();
        return records;
    }





    //@KafkaListener(topics = "${kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public JSONObject consume(String message) throws IOException {
        logger.info(String.format("\n#### -> Consumed message -> %s\n", message));
        JSONObject jsonObject = XmlUtil.convertToJSONObject(message);
        logger.info("\n" + XmlUtil.convertToPretty(jsonObject));
        return jsonObject;
    }


}

