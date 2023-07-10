package com.vs.foosh.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vs.foosh.api.services.LinkBuilder;
import com.vs.foosh.api.exceptions.*;
import com.vs.foosh.api.services.HttpResponseBuilder;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(QueryNameIsNotUniqueException.class)
    public ResponseEntity<Object> handleQueryNameIsNotUniqueException(QueryNameIsNotUniqueException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self",    LinkBuilder.getDeviceLink(exception.getId()).toString());
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                linkBlock,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(QueryNameIsNullException.class)
    public ResponseEntity<Object> handleQueryNameIsNullException(QueryNameIsNullException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self",    LinkBuilder.getDeviceLink(exception.getId()).toString());
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                linkBlock,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueryNameIsEmptyException.class)
    public ResponseEntity<Object> handleQueryNameIsEmptyException(QueryNameIsEmptyException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self",    LinkBuilder.getDeviceLink(exception.getId()).toString());
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                linkBlock,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BatchQueryNameException.class)
    public ResponseEntity<Object> handleBatchQueryNameException(BatchQueryNameException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                linkBlock,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeviceIdNotFoundException.class)
    public ResponseEntity<Object> handleDeviceIdNotFoundException(DeviceIdNotFoundException exception, WebRequest request) {
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                linkBlock,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SmartHomeAccessException.class)
    public ResponseEntity<Object> handleSmartHomeAccessException(SmartHomeAccessException exception, WebRequest request) {
        return HttpResponseBuilder.buildException(
                exception.getMessage(),
                HttpStatus.BAD_GATEWAY);
    }
    
    @ExceptionHandler(SmartHomeIOException.class)
    public ResponseEntity<Object> handleSmartHomeIOException(SmartHomeIOException exception, WebRequest request) {
        return HttpResponseBuilder.buildException(
                exception.getMessage(),
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
