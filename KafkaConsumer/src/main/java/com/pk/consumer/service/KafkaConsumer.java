package com.pk.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;
import com.pk.consumer.repository.impl.SaveAuditErrorLogImpl;

/**
 * 
 * @author nkancham
 *
 */
@Service
public class KafkaConsumer {

  @Autowired
  private SaveAuditErrorLogImpl saveAuditErrorLogImpl;


  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

  /**
   * 
   * @param customer
   */
  @KafkaListener(topics = "Kafka_Publisher_Consumer", group = "group_consumerJson",
      containerFactory = "consumerKafkaListenerFactory")
  public AuditLog consumeJson(Customer customer) {
    
    log.info("Consumed customer JSON Message: {}", customer);
    return saveAuditErrorLogImpl.saveAuditLogObject(customer);
  }
}
