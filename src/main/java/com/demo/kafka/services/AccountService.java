package com.demo.kafka.services;

import com.demo.kafka.dto.Account;
import com.demo.kafka.kafka.KafkaListener;
import com.demo.kafka.kafka.KafkaSender;
import com.demo.kafka.utils.XmlUtil;
import net.minidev.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class AccountService {


    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private KafkaSender kafkaSender;
    private KafkaListener kafkaListener;


    @Autowired
    public AccountService(KafkaSender kafkaSender, KafkaListener kafkaListener){
        this.kafkaSender = kafkaSender;
        this.kafkaListener=kafkaListener;
    }


    public void addAccount(JSONObject account) {
        org.json.JSONObject json=convertToOrgJSONObejct(account);
        String xml = XmlUtil.convertToXML(json,"root");
        kafkaSender.send(xml);
        System.out.println("finish");

    }






    public  org.json.JSONObject convertToOrgJSONObejct(net.minidev.json.JSONObject jsonObject) {
        org.json.JSONObject json = new org.json.JSONObject(jsonObject);
        return json;
    }


    public org.json.JSONObject consumeAccount() {
        ConsumerRecords<Long, String> records= kafkaListener.consumeMessages(1);
        System.out.println("xxx");
        Iterator iter = records.iterator();

        Map<Long, String> map = (Map<Long, String>)iter.next();
        String xmlStr = (String)map.keySet().toArray()[0];
        System.out.println(xmlStr);
        org.json.JSONObject jsonObject = XmlUtil.convertToJSONObject(xmlStr);
        return jsonObject;

    }
}
