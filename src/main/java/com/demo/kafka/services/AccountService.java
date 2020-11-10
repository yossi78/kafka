package com.demo.kafka.services;

import com.demo.kafka.kafka.KafkaConsumer;
import com.demo.kafka.kafka.KafkaProducer;
import com.demo.kafka.utils.XmlUtil;
import net.minidev.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AccountService {


    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private KafkaProducer kafkaProducer;
    private KafkaConsumer kafkaConsumer;


    @Autowired
    public AccountService(KafkaProducer kafkaProducer, KafkaConsumer kafkaConsumer){
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer=kafkaConsumer;
    }


    public void addAccount(JSONObject account) {
        org.json.JSONObject json=convertToOrgJSONObejct(account);
        String xml = XmlUtil.convertToXML(json,"root");
        kafkaProducer.sendMessage(xml);
        logger.info("finish to add account");
    }


    public  org.json.JSONObject convertToOrgJSONObejct(net.minidev.json.JSONObject jsonObject) {
        org.json.JSONObject json = new org.json.JSONObject(jsonObject);
        return json;
    }


    public org.json.JSONObject consumeAccount() {
        ConsumerRecords<String, String> records= kafkaConsumer.consumeMessages();
        String xml=getStringValues(records).get(0);
        logger.info(xml);
        org.json.JSONObject jsonObject = XmlUtil.convertToJSONObject(xml);
        logger.info("\n" + XmlUtil.convertToPretty(jsonObject));
        return jsonObject;
    }



    private List<String> getStringValues(ConsumerRecords<String, String> records){
        List<String> list  = new ArrayList<>();
        records.iterator().forEachRemaining(c->list.add(c.value()));
        return list;
    }
}
