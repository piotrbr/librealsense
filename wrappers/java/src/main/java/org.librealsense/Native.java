package org.librealsense;
import org.bven.jni.*;
public class Native {

    static {
        ArchLoader.load("realsense2.2", "/lib/org.librealsense", Native.class);
        ArchLoader.load(Native.class);
    }

    static int RS2_CAMERA_INFO_NAME = 0;
    static int RS2_CAMERA_INFO_SERIAL_NUMBER = 1;
    static int RS2_CAMERA_INFO_FIRMWARE_VERSION = 2;
    static int RS2_CAMERA_INFO_PHYSICAL_PORT = 3;
    static int RS2_CAMERA_INFO_DEBUG_OP_CODE = 4;
    static int RS2_CAMERA_INFO_ADVANCED_MODE = 5;
    static int RS2_CAMERA_INFO_CAMERA_LOCKED = 6;
    static int RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR = 7;
    static int RS2_CAMERA_INFO_COUNT = 8;

    native static void helloThere();

    native static long rs2CreateContext(int apiVersion);
    native static long rs2QueryDevices(long contex);
    native static int rs2GetDeviceCount(long deviceList);
    native static long rs2CreateDevice(long deviceList, int deviceIndex);

    native static int rs2SupportsDeviceInfo(long device, int info);
    native static String rs2GetDeviceInfo(long device, int info);

    public static void main(String[] args) {
        Native.helloThere();
        long context = rs2CreateContext(0);
        long deviceList = rs2QueryDevices(context);
        int deviceCount = rs2GetDeviceCount(deviceList);

        if (deviceCount > 0) {
            long device = rs2CreateDevice(deviceList, 0);
            System.out.println("supports name: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_NAME));
            System.out.println("name:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_NAME));

            System.out.println("supports serial : " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_SERIAL_NUMBER));
            System.out.println("serial:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_SERIAL_NUMBER));

            System.out.println("supports firmware version: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_FIRMWARE_VERSION));
            System.out.println("firmware version:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_FIRMWARE_VERSION));

            System.out.println("supports phyisical port: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_PHYSICAL_PORT));
            System.out.println("physical port:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_PHYSICAL_PORT));

            System.out.println("supports debug op code: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_DEBUG_OP_CODE));
            System.out.println("debyg op code:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_DEBUG_OP_CODE));

            System.out.println("supports advanced mode: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_ADVANCED_MODE));
            System.out.println("advanced mode:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_ADVANCED_MODE));

            System.out.println("supports camera locked: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_CAMERA_LOCKED));
            System.out.println("camera locked:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_CAMERA_LOCKED));

            System.out.println("supports usb type descriptor: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
            System.out.println("usb type descriptor:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
        }
        System.out.println("context " + context);
        System.out.println("devices: " + deviceCount);
    }
}

