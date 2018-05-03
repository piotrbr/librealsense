
package org.librealsense;

class Sensor {
    long sensor;
    protected Sensor(long sensor) {
        this.sensor = sensor;
    }

    public StreamProfileList getStreamProfiles() {
        return new StreamProfileList(Native.rs2GetStreamProfiles(sensor));
    }

    public String getInfo(int info) {
        return Native.rs2GetSensorInfo(sensor, info);
    }

    public boolean isExtendableTo(Native.Extension extension) {
        return Native.rs2IsSensorExtendableTo(sensor, extension.ordinal()) != 0;
    }

    public void destroy() {
        Native.rs2DeleteSensor(sensor);
    }

}