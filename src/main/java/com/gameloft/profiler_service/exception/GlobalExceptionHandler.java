package com.gameloft.profiler_service.exception;

import com.gameloft.profiler_service.model.ErrorResponse;
import org.hibernate.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ProfileException.class})
    public ResponseEntity<ErrorResponse> profileException(final Exception exception,
                                                          final WebRequest webRequest) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = {CampaignException.class, MappingException.class})
    public ResponseEntity<ErrorResponse> genericException(final Exception exception,
                                                          final WebRequest webRequest) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }
}
