package com.vs.foosh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.vs.foosh.api.model.AbstractSmartHomeCredentials;
import com.vs.foosh.api.model.AbstractDevice;
import com.vs.foosh.api.model.DeviceList;
import com.vs.foosh.api.model.FetchDeviceResponse;
import com.vs.foosh.api.model.exceptions.SmartHomeAccessException;
import com.vs.foosh.api.model.exceptions.SmartHomeIOException;
import com.vs.foosh.custom.SmartHomeCredentials;
import com.vs.foosh.services.LinkBuilder;

public abstract class AbstractDeviceController {

    protected SmartHomeCredentials smartHomeCredentials = new SmartHomeCredentials();

    //
    // Device Collection
    //

    @Bean
    CommandLineRunner initDevices() {
        return args -> {
            smartHomeCredentials.loadSmartHomeCredentials();
        };
    }
    
    @GetMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> devicesGet() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("devices", DeviceList.getDevices());
        responseBody.put("self", LinkBuilder.getDeviceListLink());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping(value = "/devices", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> devicesPost(
            @RequestBody(required = false) AbstractSmartHomeCredentials credentials) {
        FetchDeviceResponse apiResponse;

        try {
            if (credentials == null) {
                apiResponse = fetchDevicesFromSmartHomeAPI();
            } else {
                apiResponse = fetchDevicesFromSmartHomeAPI(credentials);
            }

            DeviceList.setDevices(apiResponse.getDevices());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("devices", DeviceList.getDevices());
            responseBody.put("self", LinkBuilder.getDeviceListLink());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (ResourceAccessException rAccessException) {
            throw new SmartHomeAccessException(smartHomeCredentials.getUri() + "/devices");
        } catch (IOException ioException) {
            throw new SmartHomeIOException(smartHomeCredentials.getUri() + "/devices");
        }
    }

    @PatchMapping("/devices")
    public String devicesPut() {
        //TODO: Allow batch modification (queryName).
        return "";
    }

    @DeleteMapping("/devices")
    public ResponseEntity<Object> devicesDelete() {
        DeviceList.clearDevices();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("devices", DeviceList.getDevices());
        responseBody.put("self", LinkBuilder.getDeviceListLink());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    protected abstract FetchDeviceResponse fetchDevicesFromSmartHomeAPI() throws ResourceAccessException, IOException;
    protected abstract FetchDeviceResponse fetchDevicesFromSmartHomeAPI(AbstractSmartHomeCredentials credentials) throws ResourceAccessException, IOException;

    //
    // Device
    //

    @GetMapping("/device/{id}")
    public ResponseEntity<AbstractDevice> deviceGet(@PathVariable("id") String id) {
        Optional<AbstractDevice> device = DeviceList.getDevice(id);
        // if (device.isPresent()) {
        //     return ResponseEntity.ok().body(device.get());
        // }
        
        return ResponseEntity.ok().body(device.get());
    }

    @PostMapping("/device/{id}")
    public AbstractDevice devicePost(@PathVariable("id") Long id) {
        return null;
    }

    /// Allow modification to queryName.
    @PatchMapping("/device/{id}")
    public AbstractDevice devicePut(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/device/{id}")
    public void deviceDelete(@PathVariable("id") Long id) {

    }
}