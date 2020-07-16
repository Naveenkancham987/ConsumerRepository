package com.pk.consumer.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.consumer.converter.AuditLogConverter;
import com.pk.consumer.converter.ErrorLogConverter;
import com.pk.consumer.model.Address;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;
import com.pk.consumer.model.ErrorLog;
import com.pk.consumer.model.ErrorResponse;
import com.pk.consumer.repository.AuditLogRepository;
import com.pk.consumer.repository.ErrorLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveAuditErrorLogImplTest {

  @MockBean
  private AuditLogRepository auditLogRepository;

  @MockBean
  private AuditLogConverter auditLogConverter;

  @MockBean
  private ErrorLogRepository errorLogRepository;

  @MockBean
  private ErrorLogConverter errorLogConverter;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SaveAuditErrorLogImpl saveAuditErrorLogImpl;

  @Test
  public void testSaveAuditLogObject() throws Exception {
    Mockito.when(auditLogConverter.convert(Mockito.any(Customer.class)))
        .thenReturn(createAuditLog());
    Mockito.when(auditLogRepository.save(Mockito.any(AuditLog.class)))
        .thenReturn(getMockAuditLog());
    AuditLog result = saveAuditErrorLogImpl.saveAuditLogObject(createCustomer());
    assertNotNull(result);
    assertEquals("C01234", result.getCustomerNumber());
  }

  @Test
  public void testSaveErrorLogObject() throws Exception {
    ErrorLog errorLog = createErrorLog();
    ErrorLog mockErrorLog = getMockErrorLog(errorLog);
    Mockito.when(errorLogConverter.convert(Mockito.anyString(), Mockito.any(ErrorResponse.class)))
        .thenReturn(errorLog);
    Mockito.when(errorLogRepository.save(Mockito.any(ErrorLog.class))).thenReturn(mockErrorLog);
    ErrorLog result =
        saveAuditErrorLogImpl.saveErrorLogObject(createCustomer(), createErrorResponse());
    assertNotNull(result);
    System.out.println(result.getErrorType());
    assertEquals("MethodArgumentNotValidException", result.getErrorType());
  }

  private AuditLog createAuditLog() throws JsonProcessingException {
    Customer customer = createCustomer();
    AuditLog auditLog = new AuditLog();
    auditLog.setCustomerNumber((String) customer.getCustomerNumber());
    auditLog.setPayload(objectMapper.writeValueAsString(customer));
    return auditLog;
  }

  private AuditLog getMockAuditLog() throws JsonProcessingException {
    AuditLog auditLog = createAuditLog();
    auditLog.setId(15L);
    return auditLog;
  }

  private ErrorLog getMockErrorLog(ErrorLog errorLog) {
    errorLog.setId(15L);
    return errorLog;
  }

  private ErrorLog createErrorLog() throws JsonProcessingException {
    Customer customer = createCustomer();
    ErrorResponse errorResponse = createErrorResponse();
    ErrorLog errorLog = new ErrorLog();
    errorLog.setErrorDiscription(errorResponse.getMessage());
    errorLog.setErrorType(errorResponse.getErrorType());
    errorLog.setPayload(objectMapper.writeValueAsString(customer));

    return errorLog;
  }

  private ErrorResponse createErrorResponse() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrorType("MethodArgumentNotValidException");
    errorResponse.setMessage("Email should be valid]");
    errorResponse.setStatus("ERROR");
    return errorResponse;
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("06-06-2012");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C01234");
    customer.setEmail("navin270587@gmail.com");
    customer.setFirstName("navinkancham");
    customer.setLastName("kanchamnavi");
    customer.setMobileNumber("9849558454");
    return customer;

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
