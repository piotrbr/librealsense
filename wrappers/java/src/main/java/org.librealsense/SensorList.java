package org.librealsense;

public class SensorList {

    long sensorList;

    protected SensorList(long sensorList) {
        this.sensorList = sensorList;
    }

    public int getSensorCount() {
        return Native.rs2GetSensorsCount(sensorList);
    }

    public void destroy() {
        Native.rs2DeleteSensorList(sensorList);
    }

    public Sensor createSensor(int index) {
        return new Sensor(Native.rs2CreateSensor(sensorList, index));
    }
}