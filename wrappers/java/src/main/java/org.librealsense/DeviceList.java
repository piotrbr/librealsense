package org.librealsense;
import java.util.List;
import java.util.ArrayList;

public class DeviceList {

    long deviceList;
    protected DeviceList(long deviceList) {
        this.deviceList = deviceList;
    }

    public Device createDevice(int index) {
        long device = Native.rs2CreateDevice(deviceList, index);
        return new Device(device);
    }

    public int getDeviceCount() {
        return Native.rs2GetDeviceCount(deviceList);
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<Device>();
        int deviceCount = getDeviceCount();
        for (int i = 0; i < deviceCount; ++i) {
            devices.add(createDevice(i));
        }
        return devices;
    }
}