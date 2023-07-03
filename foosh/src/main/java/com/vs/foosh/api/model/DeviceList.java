package com.vs.foosh.api.model;

import java.util.ArrayList;

import com.vs.foosh.api.model.Exceptions.QueryNameIsNotUniqueException;

public class DeviceList {
    private static ArrayList<BaseDevice> devices;

    public static ArrayList<BaseDevice> getInstance() {
        if (devices == null) {
            devices = new ArrayList<BaseDevice>();
        }

        return devices;
    }

    public static void setDevices(ArrayList<BaseDevice> deviceList) {
        devices = deviceList;
    }

    public static void pushDevice(BaseDevice device) {
        if (isAUniqueQueryName(device.getQueryName())) {
            devices.add(device);
        } else {
            throw new QueryNameIsNotUniqueException(device.getQueryName());
        }
    }

    public static ArrayList<BaseDevice> getDevices() {
        return devices;
    }

    public static boolean isAUniqueQueryName(String name) {
        for (BaseDevice d: devices) {
            if (d.getQueryName().equals(name)) {
                return false;
            }
        }
        
        return true;
    }

}
