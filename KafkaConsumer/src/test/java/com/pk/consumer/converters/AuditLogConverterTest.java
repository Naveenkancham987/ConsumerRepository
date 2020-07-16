package com.pk.consumer.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import com.pk.consumer.converter.AuditLogConverter;
import com.pk.consumer.model.Address;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;


public class AuditLogConverterTest {

  private AuditLogConverter auditLogConverter;

  @Before
  public void setup() {
    auditLogConverter = new AuditLogConverter();
  }

  @Test
  public void testConvertReturnAuditLogObject() {
    AuditLog result = auditLogConverter.convert(createCustomer());
    assertNotNull(result);
    assertEquals("C000001", result.getCustomerNumber());
    assertEquals(createCustomer().toString(), result.getPayload());
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setAddress(createAddress());
    customer.setBirthdate("2-12-2012");
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setCustomerNumber("C000001");
    customer.setEmail("email@gmail.com");
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
