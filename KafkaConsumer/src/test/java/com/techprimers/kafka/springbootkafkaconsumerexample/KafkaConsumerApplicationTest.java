package com.techprimers.kafka.springbootkafkaconsumerexample;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.pk.consumer.KafkaConsumerApplication;

@SpringBootTest
public class KafkaConsumerApplicationTest {

  @Test
  public void applicationContextTest() {
    KafkaConsumerApplication.main(new String[] {});
  }

}
