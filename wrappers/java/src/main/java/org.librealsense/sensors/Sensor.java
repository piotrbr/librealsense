package org.librealsense.sensors;

import org.librealsense.Native;
import org.librealsense.streamprofiles.StreamProfileList;

public class Sensor {
    long instance;

    public Sensor(long instance) {
        this.instance = instance;
    }

    public StreamProfileList getStreamProfiles() {
        return new StreamProfileList(Native.rs2GetStreamProfiles(instance));
    }

    public String getInfo(int info) {
        return Native.rs2GetSensorInfo(instance, info);
    }

    public boolean isExtendableTo(Native.Extension extension) {
        return Native.rs2IsSensorExtendableTo(instance, extension.ordinal()) != 0;
    }

    public void destroy() {
        Native.rs2DeleteSensor(instance);
    }

    public float getDepthScale() {
        return Native.rs2GetDepthScale(instance);
    }

    public boolean supportsInfo(int cameraInfo)
    {
        return Native.rs2SupportsSensorInfo(instance,cameraInfo) != 0;
    }
}