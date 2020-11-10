package com.demo.kafka.api;

import com.demo.kafka.services.AccountService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping(value = "/v1/kafka")
public class KafkaController {


    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);
    private AccountService accountService;


    @Autowired
    public KafkaController(AccountService accountService){
        this.accountService = accountService;
    }




    @PostMapping
    public ResponseEntity addAccount(@RequestBody JSONObject account) {
        try {
            accountService.addAccount(account);
            org.json.JSONObject resultJson = accountService.consumeAccount();
            logger.info("Account has been added successfully");
            return new ResponseEntity(resultJson.toString(),HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new Account",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping(value = "/health")
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("Account Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
