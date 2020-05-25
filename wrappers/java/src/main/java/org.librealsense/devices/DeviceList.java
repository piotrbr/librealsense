package org.librealsense.devices;

import org.librealsense.Native;

import java.util.ArrayList;
import java.util.List;

public class DeviceList {

    long instance;

    public DeviceList(long instance) {
        this.instance = instance;
    }

    public Device createDevice(int index) {
        long device = Native.rs2CreateDevice(instance, index);
        return new Device(device);
    }

    public int getDeviceCount() {
        return Native.rs2GetDeviceCount(instance);
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<Device>();
        int deviceCount = getDeviceCount();
        for (int i = 0; i < deviceCount; ++i) {
            devices.add(createDevice(i));
        }
        return devices;
    }

    public void delete() {
        Native.rs2DeleteDeviceList(instance);
    }
}