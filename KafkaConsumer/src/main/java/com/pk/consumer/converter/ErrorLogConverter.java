package com.pk.consumer.converter;

import org.springframework.stereotype.Component;
import com.pk.consumer.model.ErrorLog;
import com.pk.consumer.model.ErrorResponse;

@Component
public class ErrorLogConverter {

  public ErrorLog convert(String payload, ErrorResponse errorResponse) {
    ErrorLog errorLog = new ErrorLog();
    errorLog.setErrorDiscription(errorResponse.getMessage());
    errorLog.setErrorType(errorResponse.getErrorType());
    errorLog.setPayload(payload);
    return errorLog;
  }


}
