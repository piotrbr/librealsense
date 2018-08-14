package org.librealsense;

public class Device {

    private long instance;

    protected Device(long instance) {
        this.instance = instance;
    }

    public String serial() {
        return Native.rs2GetDeviceInfo(instance, Native.RS2_CAMERA_INFO_SERIAL_NUMBER);
    }

    public String name() {
        return Native.rs2GetDeviceInfo(instance, Native.RS2_CAMERA_INFO_NAME);
    }

    public SensorList querySensors() {
        return new SensorList(Native.rs2QuerySensors(instance));
    }
}