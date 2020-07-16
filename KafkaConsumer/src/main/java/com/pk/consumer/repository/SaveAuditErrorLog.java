package com.pk.consumer.repository;

import org.springframework.stereotype.Service;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;
import com.pk.consumer.model.ErrorLog;
import com.pk.consumer.model.ErrorResponse;

@Service
public interface SaveAuditErrorLog {

    public AuditLog saveAuditLogObject(Customer customer);

    public ErrorLog saveErrorLogObject(Customer request, ErrorResponse errorResponse);
}
