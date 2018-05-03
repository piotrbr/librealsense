package org.librealsense;

public class Device {

    private long device;
    protected Device(long device) {
        this.device = device;
    }

    public String serial() {
        return Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_SERIAL_NUMBER );
    }
    public String name() { return Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_NAME); }

}