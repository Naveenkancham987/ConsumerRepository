package com.pk.consumer.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.consumer.converter.ErrorLogConverter;
import com.pk.consumer.model.Address;
import com.pk.consumer.model.Customer;
import com.pk.consumer.model.ErrorLog;
import com.pk.consumer.model.ErrorResponse;

public class ErrorLogConverterTest {

  private ErrorLogConverter errorLogConverter;

  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    errorLogConverter = new ErrorLogConverter();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testConvertReturnErrorLogObject() throws Exception {
    String payload = objectMapper.writeValueAsString(createCustomer());
    ErrorLog result = errorLogConverter.convert(payload, createErrorResponse());
    assertNotNull(result);
    assertEquals("MethodArgumentNotValidException", result.getErrorType());


  }

  private ErrorResponse createErrorResponse() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrorType("MethodArgumentNotValidException");
    errorResponse.setMessage("[email: Email should be valid]");
    errorResponse.setStatus("ERROR");
    return errorResponse;
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("2-12-2012");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C000001");
    customer.setEmail("email@@gmail.com");
    customer.setFirstName("Foo");
    customer.setLastName("boo");
    customer.setMobileNumber("1234567890");
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
