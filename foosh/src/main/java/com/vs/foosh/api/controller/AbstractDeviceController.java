package com.vs.foosh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.vs.foosh.api.model.AbstractSmartHomeCredentials;
import com.vs.foosh.api.exceptions.*;
import com.vs.foosh.api.model.AbstractDevice;
import com.vs.foosh.api.model.DeviceList;
import com.vs.foosh.api.model.FetchDeviceResponse;
import com.vs.foosh.api.services.LinkBuilder;
import com.vs.foosh.api.services.HttpResponseBuilder;
import com.vs.foosh.custom.SmartHomeCredentials;

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
        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self", LinkBuilder.getDeviceListLink().toString());

        return HttpResponseBuilder.buildResponse(
                new AbstractMap.SimpleEntry<String, Object>("devices", DeviceList.getDevices()),
                linkBlock,
                HttpStatus.OK);
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

            Map<String, String> linkBlock = new HashMap<>();
            linkBlock.put("self", LinkBuilder.getDeviceListLink().toString());

            return HttpResponseBuilder.buildResponse(
                    new AbstractMap.SimpleEntry<String, Object>("devices", DeviceList.getDevices()),
                    linkBlock,
                    HttpStatus.OK);
        } catch (ResourceAccessException rAccessException) {
            throw new SmartHomeAccessException(smartHomeCredentials.getUri() + "/devices");
        } catch (IOException ioException) {
            throw new SmartHomeIOException(smartHomeCredentials.getUri() + "/devices");
        }
    }

    @PutMapping("/devices")
    public ResponseEntity<Object> devicesPut() {
        throw new HttpMappingNotImplementedException(
                "You can only update the devices list with POST!",
                Map.of("self", LinkBuilder.getDeviceListLink().toString()));
    }

    @PatchMapping("/devices")
    public String devicesPatch() {
        // TODO: Allow batch modification (queryName).
        return "";
    }

    @DeleteMapping("/devices")
    public ResponseEntity<Object> devicesDelete() {
        DeviceList.clearDevices();

        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("self", LinkBuilder.getDeviceListLink().toString());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("devices", DeviceList.getDevices());
        responseBody.put("links", linkBlock);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    protected abstract FetchDeviceResponse fetchDevicesFromSmartHomeAPI() throws ResourceAccessException, IOException;

    protected abstract FetchDeviceResponse fetchDevicesFromSmartHomeAPI(AbstractSmartHomeCredentials credentials)
            throws ResourceAccessException, IOException;

    //
    // Device
    //

    // Remove link duplication
    @GetMapping("/device/{id}")
    public ResponseEntity<Object> deviceGet(@PathVariable("id") String id) {
        AbstractDevice device = DeviceList.getDevice(id);

        Map<String, String> linkBlock = new HashMap<>();
        linkBlock.put("selfStatic", LinkBuilder.getDeviceLink(DeviceList.getDevice(id).getId().toString()).toString());
        linkBlock.put("selfQuery", LinkBuilder.getDeviceLink(DeviceList.getDevice(id).getQueryName()).toString());
        linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("device", device);
        responseBody.put("links", linkBlock);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/device/{id}")
    public ResponseEntity<Object> devicePost(@PathVariable("id") String id) {
        throw new HttpMappingNotImplementedException(
                "You can only create/replace a device with either POST or PATCH on /devices !",
                Map.of("devices", LinkBuilder.getDeviceListLink().toString()));
    }

    @PutMapping("/device/{id}")
    public ResponseEntity<Object> devicePut(@PathVariable("id") String id) {
        throw new HttpMappingNotImplementedException(
                "You can only create/replace a device with either POST or PATCH on /devices !",
                Map.of("devices", LinkBuilder.getDeviceListLink().toString()));
    }

    /// no empty string
    @PatchMapping("/device/{id}")
    public ResponseEntity<Object> devicePatch(@PathVariable("id") String id,
            @RequestBody Map<String, String> requestBody) {
        String queryName = requestBody.get("queryName");

        // Is a field called 'queryName'?
        if (queryName == null) {
            throw new QueryNameIsNullException(id, requestBody);
        }

        // Does the field contain any letters, i.e., is it not empty?
        if (queryName.trim().isEmpty()) {
            throw new QueryNameIsEmptyException(id, requestBody);
        }

        // Is the name provided by the field unique or the same as the current
        // queryName?
        if (DeviceList.getDevice(id).getQueryName().equals(queryName) || DeviceList.isAUniqueQueryName(queryName)) {
            DeviceList.getDevice(id).setQueryName(queryName);

            Map<String, String> linkBlock = new HashMap<>();
            linkBlock.put("selfStatic", DeviceList.getDevice(id).getStaticLink().toString());
            linkBlock.put("selfQuery", DeviceList.getDevice(id).getQueryLink().toString());
            linkBlock.put("devices", LinkBuilder.getDeviceListLink().toString());

            return HttpResponseBuilder.buildResponse(
                    DeviceList.getDevice(id),
                    linkBlock,
                    HttpStatus.OK);
        } else {
            throw new QueryNameIsNotUniqueException(id, queryName);
        }
    }

    @DeleteMapping("/device/{id}")
    public ResponseEntity<Object> deviceDelete(@PathVariable("id") String id) {
        throw new HttpMappingNotImplementedException(
                "You cannot delete an individual device. You can only delete the entire collection with DELETE on /devices !",
                Map.of("devices", LinkBuilder.getDeviceListLink().toString()));
    }
}