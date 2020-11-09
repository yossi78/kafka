package com.demo.kafka.kafka;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KafkaListener {


    private Consumer<Long, String> consumer;
    private Logger logger = LoggerFactory.getLogger(KafkaListener.class);
    private final Integer RETRY_COUNT=5;


    @Autowired
    public KafkaListener(Consumer<Long, String> consumer){
        this.consumer=consumer;
        System.out.println("");
    }




    public ConsumerRecords<Long, String> consumeMessages(int count){
        ConsumerRecords<Long, String> consumerRecords=null;

        for(int i=0;i<5;i++) {
            consumerRecords = consumer.poll(count);
            if(consumerRecords.isEmpty()){
                logger.info("TRY TO CONSUME AGAIN");
               continue;
            }
            consumerRecords.iterator().forEachRemaining(record -> logger.info(record.value()));
            consumer.commitSync();
        }
        consumer.close();
        System.out.println("DONE");
        return consumerRecords;

    }

}
