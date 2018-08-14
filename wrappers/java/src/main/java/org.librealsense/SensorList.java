package org.librealsense;

import java.util.ArrayList;
import java.util.List;

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

    public List<Sensor> getSensors() {
        List<Sensor> sensors = new ArrayList<Sensor>();
        int sensorCount = getSensorCount();
        for (int i = 0; i < sensorCount; ++i) {
            sensors.add(createSensor(i));
        }
        return sensors;
    }
}