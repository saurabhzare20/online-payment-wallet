package com.example.validateTransaction;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MyKafkaConsumer {

    @Autowired
    private DataStorageService dataStorageService;

     @KafkaListener(topics = "login", groupId = "user3")
    public void listen(ConsumerRecord<String, String> record) throws SQLException {

        System.out.println("Received message: " + record.value());
                 tasks.stringToObject(record.value());
         String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
         String username = "root";
         String password = "0000";

         Connection connection= tasks.establishConnection(jdbcUrl,username,password);
        transaction t =  tasks.stringToObject(record.value());
      boolean ans= tasks.isPinCorrect(connection,t.sender_id,t.pin);
      System.out.println(ans);




      if(!tasks.isPinCorrect(connection,t.sender_id,t.pin)){
          dataStorageService.storeData(t.request_id,"incorrect pin");
          System.out.println();
      }
      else if (!tasks.isReciverExixt(connection,t.receiver_id)) {
          dataStorageService.storeData(t.request_id,"reciver not exist");

      } else if (!tasks.isSufficientBalance(connection,t.sender_id,t.amount)) {
          dataStorageService.storeData(t.request_id,"low balance");
      } else {
          tasks.performTransaction(connection,t);
          dataStorageService.storeData(t.request_id,"transaction successful");

      }


     }
}


