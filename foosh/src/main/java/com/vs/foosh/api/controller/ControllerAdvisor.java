package com.vs.foosh.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vs.foosh.api.exceptions.DeviceIdNotFoundException;
import com.vs.foosh.api.exceptions.HttpMappingNotImplementedException;
import com.vs.foosh.api.exceptions.QueryNameIsNotUniqueException;
import com.vs.foosh.api.exceptions.SmartHomeAccessException;
import com.vs.foosh.api.exceptions.SmartHomeIOException;
import com.vs.foosh.api.services.LinkBuilder;
import com.vs.foosh.api.services.HttpResponseBuilder;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(QueryNameIsNotUniqueException.class)
    public ResponseEntity<Object> handleQueryNameIsNotUniqueException(QueryNameIsNotUniqueException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self",    LinkBuilder.getDeviceLink(exception.getId()).toString());
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                "The query name '" + exception.getId() + "' is not unique!",
                linkBlock,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeviceIdNotFoundException.class)
    public ResponseEntity<Object> handleDeviceIdNotFoundException(DeviceIdNotFoundException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                "Could not find device with id '" + exception.getId() + "' !",
                linkBlock,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SmartHomeAccessException.class)
    public ResponseEntity<Object> handleSmartHomeAccessException(SmartHomeAccessException exception, WebRequest request) {
        return HttpResponseBuilder.buildException(
                "Could not access Smart Home API at '" + exception.getUri() + "'!",
                HttpStatus.BAD_GATEWAY);
    }
    
    @ExceptionHandler(SmartHomeIOException.class)
    public ResponseEntity<Object> handleSmartHomeIOException(SmartHomeIOException exception, WebRequest request) {
        return HttpResponseBuilder.buildException(
                "A timeout occurred while tryping to retrieve device list from Smart Home API at '" + exception.getUri() + "'!",
                HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(HttpMappingNotImplementedException.class)
    public ResponseEntity<Object> handleHttpMappingNotImplementedExeption(HttpMappingNotImplementedException exception, WebRequest request) {
        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                exception.getReturnPath(),
                HttpStatus.NOT_IMPLEMENTED);
    }
}
