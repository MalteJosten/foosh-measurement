package com.vs.foosh.api.model.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vs.foosh.services.LinkBuilder;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(QueryNameIsNotUniqueException.class)
    public ResponseEntity<Object> handleQueryNameIsNotUniqueException(QueryNameIsNotUniqueException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", "The query name '" + exception.getId() + "' is not unique!");
        body.put("self", LinkBuilder.getDeviceLink(exception.getId()));
        body.put("devices", LinkBuilder.getDeviceListLink());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeviceIdNotFoundException.class)
    public ResponseEntity<Object> handleDeviceIdNotFoundException(DeviceIdNotFoundException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", "Could not find device with id '" + exception.getId() + "'!");
        body.put("devices", LinkBuilder.getDeviceListLink());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SmartHomeAccessException.class)
    public ResponseEntity<Object> handleSmartHomeAccessException(SmartHomeAccessException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", "Could not access Smart Home API at '" + exception.getUri() + "'!");

        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }
    
    @ExceptionHandler(SmartHomeIOException.class)
    public ResponseEntity<Object> handleSmartHomeIOException(SmartHomeIOException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", "A timeout occurred while tryping to retrieve device list from Smart Home API at '" + exception.getUri() + "'!");

        return new ResponseEntity<>(body, HttpStatus.GATEWAY_TIMEOUT);
    }
}
