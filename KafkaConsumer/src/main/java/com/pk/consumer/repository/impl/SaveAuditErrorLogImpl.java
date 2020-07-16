package com.pk.consumer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.consumer.converter.AuditLogConverter;
import com.pk.consumer.converter.ErrorLogConverter;
import com.pk.consumer.model.AuditLog;
import com.pk.consumer.model.Customer;
import com.pk.consumer.model.ErrorLog;
import com.pk.consumer.model.ErrorResponse;
import com.pk.consumer.repository.AuditLogRepository;
import com.pk.consumer.repository.ErrorLogRepository;
import com.pk.consumer.repository.SaveAuditErrorLog;

@Repository
public class SaveAuditErrorLogImpl implements SaveAuditErrorLog {

  private static final Logger log = LoggerFactory.getLogger(SaveAuditErrorLogImpl.class);

  @Autowired
  private AuditLogRepository auditLogRepository;

  @Autowired
  private ErrorLogRepository errorLogRepository;

  @Autowired
  private AuditLogConverter auditLogConverter;

  @Autowired
  private ErrorLogConverter errorLogConverter;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public AuditLog saveAuditLogObject(Customer customer) {
    AuditLog auditLog = auditLogConverter.convert(customer);
    log.info("auditLog object saved in DB{}", auditLog);
    return auditLogRepository.save(auditLog);
    
    /*
     * AuditLog response = null; try { AuditLog auditLog = auditLogConverter.convert(customer);
     * log.info("auditLog object saved in DB{}", auditLog); response =
     * auditLogRepository.save(auditLog); } catch (IllegalStateException exception) { ErrorResponse
     * errorResponse = prepareErrorResponse(exception); saveErrorLogObject(customer, errorResponse);
     * }
     * 
     * return response;
     */
  }

  @Override
  public ErrorLog saveErrorLogObject(Customer request, ErrorResponse errorResponse) {
    String payload = null;
    try {
      payload = objectMapper.writeValueAsString(request);
    } catch (JsonProcessingException exception) {
      log.error("exception occured during parsing{}", exception.getMessage());
    }
    ErrorLog errorLog = errorLogConverter.convert(payload, errorResponse);
    log.info("errorLog object saving in DB{}", errorResponse);
    return errorLogRepository.save(errorLog);
  }

  private ErrorResponse prepareErrorResponse(Exception exception) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrorType(exception.getClass().getSimpleName());
    errorResponse.setMessage(exception.getLocalizedMessage());
    errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
    return errorResponse;
  }

}
