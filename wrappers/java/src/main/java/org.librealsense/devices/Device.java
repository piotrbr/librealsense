package org.librealsense.devices;

import org.librealsense.Native;
import org.librealsense.sensors.SensorList;

public class Device {

    private long instance;

    public Device(long instance) {
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

    public void delete() {
        Native.rs2DeleteDevice(instance);
    }

    public void hardwareReset() {
        Native.rs2HardwareReset(instance);
    }

    public long getPlaybackDuration() {
        return Native.rs2PlaybackGetDuration(instance);
    }

    public void playbackSeek(long time) {
        Native.rs2PlaybackSeek(instance, time);
    }

    public long getPlaybackPosition() {
        return Native.rs2PlaybackGetPosition(instance);
    }

    public void playbackPause() {
        Native.rs2PlaybackDevicePause(instance);
    }

    public void playbackResume() {
        Native.rs2PlaybackDeviceResume(instance);
    }

    public void playbackStop() {
        Native.rs2PlaybackDeviceStop(instance);
    }

    public String gwtPlaybackCurrentStatus() {
        return Native.rs2PlaybackDeviceGetCurrentStatus(instance);
    }
}