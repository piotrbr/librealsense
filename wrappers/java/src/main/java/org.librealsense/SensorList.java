package org.librealsense;

public class SensorList {

    long instance;

    protected SensorList(long instance) {
        this.instance = instance;
    }

    public int getSensorCount() {
        return Native.rs2GetSensorsCount(instance);
    }

    public void destroy() {
        Native.rs2DeleteSensorList(instance);
    }

    public Sensor createSensor(int index) {
        return new Sensor(Native.rs2CreateSensor(instance, index));
    }
}