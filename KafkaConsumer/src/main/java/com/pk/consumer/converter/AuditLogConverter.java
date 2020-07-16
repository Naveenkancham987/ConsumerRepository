package com.pk.consumer.converter;

import org.springframework.stereotype.Component;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;

@Component
public class AuditLogConverter {

  public AuditLog convert(Customer customer) {
    AuditLog auditLog = new AuditLog();
    String customerNumber = customer.getCustomerNumber();
    String payload = customer.toString();
    auditLog.setCustomerNumber(customerNumber);
    auditLog.setPayload(payload);
    return auditLog;
  }


}
