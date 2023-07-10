package com.vs.foosh.api.model;

import java.util.ArrayList;
import java.util.List;

import com.vs.foosh.api.exceptions.CouldNotFindUniqueQueryNameException;
import com.vs.foosh.api.exceptions.DeviceIdNotFoundException;
import com.vs.foosh.api.exceptions.QueryNameIsNotUniqueException;

public class DeviceList {
    private static List<AbstractDevice> devices;
    private static final int UNIQUE_QUERY_NAME_TIMEOUT = 25;

    public static List<AbstractDevice> getInstance() {
        if (devices == null) {
            devices = new ArrayList<AbstractDevice>();
        }

        return devices;
    }

    public static void setDevices(List<AbstractDevice> deviceList) {
        if (devices != null) {
            clearDevices();
        }

        getInstance().addAll(deviceList);
    }

    public void pushDevice(AbstractDevice device) {
        if (isAUniqueQueryName(device.getQueryName())) {
            getInstance().add(device);
        } else {
            throw new QueryNameIsNotUniqueException(new QueryNamePatchRequest(device.getId().toString(), device.getQueryName()));
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
    public static AbstractDevice getDevice(String id) {
        for (AbstractDevice device: getDevices()) {
            if (device.id.toString().equals(id) || device.queryName.equals(id)) {
                return device;
            }
        }

        throw new DeviceIdNotFoundException(id);
    }

    public static boolean isAUniqueQueryName(String name) {
        for (AbstractDevice d: getInstance()) {
            if (d.getQueryName().equals(name)) {
                return false;
            }
        }
        
        return true;
    }

    /// Check whether the given request contains an unique (new) queryName.
    /// If the queryName is not unique, try and find another unique one by
    /// appending incrementing numbers to deviceName.
    public static String findUniqueQueryName(QueryNamePatchRequest request) {
        StringBuilder queryName = new StringBuilder(request.getQueryName());
        String id = request.getId();

        // Does the field contain any letters, i.e., is it not empty?
        if (queryName.toString().trim().isEmpty()) {
            queryName.replace(0, queryName.length(), getDevice(id).getDeviceName());
        }

        for (int i = 0; i < UNIQUE_QUERY_NAME_TIMEOUT; i++) {
            // Is the name provided by the field unique or the same as the current
            // queryName?
            if (getDevice(id).getQueryName().equals(queryName.toString()) || isAUniqueQueryName(queryName.toString())) {
                return queryName.toString();
            } else {
                queryName.replace(0, queryName.length(), getDevice(id).getDeviceName() + (i+1));
            }
        }

        throw new CouldNotFindUniqueQueryNameException(id, UNIQUE_QUERY_NAME_TIMEOUT);
    }

}
