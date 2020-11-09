package com.demo.kafka.kafka;
import com.demo.kafka.utils.XmlUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;





@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);



    @KafkaListener(topics = "${kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public JSONObject consume(String message) throws IOException {
        logger.info(String.format("\n#### -> Consumed message -> %s\n", message));
        JSONObject jsonObject = XmlUtil.convertToJSONObject(message);
       // logger.info("\n" + XmlUtil.convertToPretty(jsonObject));
        return jsonObject;
    }


}

