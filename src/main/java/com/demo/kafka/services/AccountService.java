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
import java.util.Iterator;
import java.util.Map;



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
        System.out.println("finish");

    }






    public  org.json.JSONObject convertToOrgJSONObejct(net.minidev.json.JSONObject jsonObject) {
        org.json.JSONObject json = new org.json.JSONObject(jsonObject);
        return json;
    }


    public org.json.JSONObject consumeAccount() {




//        ConsumerRecords<Long, String> records= null;
//        System.out.println("xxx");
//        Iterator iter = records.iterator();

//        Map<Long, String> map = (Map<Long, String>)iter.next();
//        String xmlStr = (String)map.keySet().toArray()[0];
//        System.out.println(xmlStr);
        org.json.JSONObject jsonObject = XmlUtil.convertToJSONObject("xmlStr");
        return jsonObject;

    }
}
