package com.vs.foosh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vs.foosh.api.model.BaseDevice;
import com.vs.foosh.api.model.DeviceList;

@RestController
public class DeviceController {

    private DeviceList devices;

    //
    // Device Collection
    //

    @Bean
    CommandLineRunner initDevices() {
        return args -> {
            // TODO: check persistent config file for devices
        };
    }
    
    @GetMapping("/devices")
    public DeviceList devicesGet() {
        return new DeviceList();
    }

    @PostMapping("/devices")
    public String devicesPost() {
        //TODO: - Fetch devices
        //      - try construct unique name
        return "";
    }

    @PutMapping("/devices")
    public String devicesPut() {
        return "";
    }

    @DeleteMapping("/devices")
    public void devicesDelete() {
    }

    //
    // Device
    //

    @GetMapping("/device")
    public BaseDevice deviceGet() {
        return new BaseDevice();
    }

    @PostMapping("/device")
    public BaseDevice devicePost() {
        return new BaseDevice();
    }

    @PutMapping("/device")
    public BaseDevice devicePut() {
        return new BaseDevice();
    }

    @DeleteMapping("/device")
    public void deviceDelete() {
    }
}