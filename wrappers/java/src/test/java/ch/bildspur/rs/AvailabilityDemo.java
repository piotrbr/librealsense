package ch.bildspur.rs;

import org.librealsense.Context;
import org.librealsense.devices.DeviceList;
import org.librealsense.Native;

public class AvailabilityDemo {

    public static void main(String[] args)
    {
        Native.loadNativeLibraries("libs");

        Context context = Context.create();
        DeviceList devices = context.queryDevices();

        if(devices.getDeviceCount() > 0)
            System.out.println("device available!");
        else
            System.out.println("no device available!");

        context.delete();
    }
}
