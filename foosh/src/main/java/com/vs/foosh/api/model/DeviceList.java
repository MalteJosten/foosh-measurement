package com.vs.foosh.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vs.foosh.api.exceptions.QueryNameIsNotUniqueException;

public class DeviceList {
    private static List<AbstractDevice> devices;

    public static List<AbstractDevice> getInstance() {
        if (devices == null) {
            devices = new ArrayList<AbstractDevice>();
        }

        return devices;
    }

    public static void setDevices(List<AbstractDevice> deviceList) {
        devices = deviceList;
    }

    public void pushDevice(AbstractDevice device) {
        if (isAUniqueQueryName(device.getQueryName())) {
            getInstance().add(device);
        } else {
            throw new QueryNameIsNotUniqueException(device.getId().toString(), device.getQueryName());
        }
    }

    public static List<AbstractDevice> getDevices() {
        return getInstance();
    }

    public static void clearDevices() {
        getInstance().clear();
    }

    ///
    /// Let the client search for a device by
    /// (1) it's ID,
    /// (2) it's queryName
    ///
    public static Optional<AbstractDevice> getDevice(String id) {
        Optional<AbstractDevice> foundDevice = Optional.empty();

        for (AbstractDevice device: getDevices()) {
            if (device.id.toString().equals(id) || device.queryName.equals(id)) {
                foundDevice = Optional.of(device);
                break;
            }
        }

        return foundDevice;

    }

    public static boolean isAUniqueQueryName(String name) {
        for (AbstractDevice d: getInstance()) {
            if (d.getQueryName().equals(name)) {
                return false;
            }
        }
        
        return true;
    }

}
