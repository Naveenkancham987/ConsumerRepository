package com.pk.consumer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.pk.consumer.model.Address;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;
import com.pk.consumer.repository.ErrorLogRepository;
import com.pk.consumer.repository.impl.SaveAuditErrorLogImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConsumerTest {

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @MockBean
  private SaveAuditErrorLogImpl saveAuditErrorLogImpl;
  
  @Mock
  private ErrorLogRepository errorLog;

  @Test
  public void testConsumeJson() {
    when(saveAuditErrorLogImpl.saveAuditLogObject(createCustomer())).thenReturn(getAuditLog());
    AuditLog auditLog = kafkaConsumer.consumeJson(createCustomer());
    assertNotNull(auditLog);
    assertEquals("C1234", auditLog.getCustomerNumber());
    System.out.println(auditLog.getCustomerNumber());
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("02-12-2019");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C000001");
    customer.setEmail("navin27@gmail.com");
    customer.setFirstName("Foofoofoof");
    customer.setLastName("boobooboob");
    customer.setMobileNumber("9234567890");
    return customer;
  }

  private AuditLog getAuditLog() {
    AuditLog auditLog = new AuditLog();
    auditLog.setCustomerNumber("C1234");
    auditLog.setId(100L);
    return auditLog;
  }

  private Address createAddress() {
    Address address = new Address();
    address.setAddressLine1("AddressLine1");
    address.setAddressLine2("AddressLine2");
    address.setPostalCode("12345");
    address.setStreet("Street");
    return address;
  }

}
