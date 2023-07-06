package com.vs.foosh.api.services;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vs.foosh.api.model.AbstractDevice;

public class HttpResponseBuilder {
    public static ResponseEntity<Object> buildResponse(AbstractDevice device, Map<String, String> linkBlock, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("device", device);
        responseBody.put("links", linkBlock);

        return new ResponseEntity<>(responseBody, status);
    }

    public static ResponseEntity<Object> buildResponse(AbstractMap.Entry<String, Object> result, Map<String, String> linkBlock, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(result.getKey(), result.getValue());
        responseBody.put("links", linkBlock);

        return new ResponseEntity<>(responseBody, status);
    }

    public static ResponseEntity<Object> buildException(String message, Map<String, String> linkBlock, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("links", linkBlock);

        return new ResponseEntity<>(responseBody, status);
    }

    public static ResponseEntity<Object> buildException(String message, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);

        return new ResponseEntity<>(responseBody, status);
    }
}
