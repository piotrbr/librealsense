package org.realsense.test;

import org.librealsense.*;

import static org.realsense.test.TestUtil.loadLibraries;

public class ApiTest {
    public static void main(String[] args) {
        loadLibraries();

        // create context
        Context context = Context.create();

        // find devices
        DeviceList devices = context.queryDevices();
        System.out.println("There are " + devices.getDeviceCount() + " Devices!");

        // get first device
        Device device = devices.getDevices().get(0);
        SensorList sensors = device.querySensors();

        for(Sensor sensor :  sensors.getSensors())
        {
            if(sensor.supportsInfo(Native.RS2_CAMERA_INFO_NAME))
                System.out.println(sensor.getInfo(Native.RS2_CAMERA_INFO_NAME));
        }
    }
}
