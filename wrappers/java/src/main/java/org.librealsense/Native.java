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


    public static enum Stream {
        RS2_STREAM_ANY,
        RS2_STREAM_DEPTH,
        RS2_STREAM_COLOR,
        RS2_STREAM_INFRARED,
        RS2_STREAM_FISHEYE,
        RS2_STREAM_GYRO,
        RS2_STREAM_ACCEL,
        RS2_STREAM_GPIO,
        RS2_STREAM_POSE,
        RS2_STREAM_CONFIDENCE,
        RS2_STREAM_COUNT
    }

    public static enum Format {
        RS2_FORMAT_ANY,
        RS2_FORMAT_Z16,
        RS2_FORMAT_DISPARITY16,
        RS2_FORMAT_XYZ32F,
        RS2_FORMAT_YUYV,
        RS2_FORMAT_RGB8,
        RS2_FORMAT_BGR8,
        RS2_FORMAT_RGBA8,
        RS2_FORMAT_BGRA8,
        RS2_FORMAT_Y8,
        RS2_FORMAT_Y16,
        RS2_FORMAT_RAW10,
        RS2_FORMAT_RAW16,
        RS2_FORMAT_RAW8,
        RS2_FORMAT_UYVY,
        RS2_FORMAT_MOTION_RAW,
        RS2_FORMAT_MOTION_XYZ32F,
        RS2_FORMAT_GPIO_RAW,
        RS2_FORMAT_6DOF,
        RS2_FORMAT_DISPARITY32,
        RS2_FORMAT_COUNT
    }

    native static void helloThere();

    native static long rs2CreateContext(int apiVersion);

    native static long rs2QueryDevices(long contex);

    native static int rs2GetDeviceCount(long deviceList);

    native static long rs2CreateDevice(long deviceList, int deviceIndex);

    native static int rs2SupportsDeviceInfo(long device, int info);

    native static String rs2GetDeviceInfo(long device, int info);

    native static long rs2CreatePipeline(long context);

    native static long rs2CreateConfig();

    native static void rs2ConfigEnableStream(long config, int stream, int index, int width, int height, int format, int framerate);

    native static long rs2PipelineStartWithConfig(long pipeline, long config);


    native static long rs2PipelineWaitForFrames(long pipeline, int timeOut);
    native static int rs2EmbeddedFramesCount(long frames);
    native static long rs2ExtractFrame(long frames, int index);
    native static long rs2ReleaseFrame(long frame);
    native static int rs2IsFrameExtendableTo(long frame, int extension);
    native static int rs2GetFrameWidth(long frame);
    native static int rs2GetFrameHeight(long frame);
    native static float rs2DepthFrameGetDistance(long frame, int x, int y);




    public static void main(String[] args) {
        Native.helloThere();
        long context = rs2CreateContext(0);
        long deviceList = rs2QueryDevices(context);
        int deviceCount = rs2GetDeviceCount(deviceList);

        System.out.println("devices: " + deviceCount);

        for (int i = 0; i < deviceCount; ++i) {
            long device = rs2CreateDevice(deviceList, i);
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


        long pipeline = rs2CreatePipeline(context);
        long config = rs2CreateConfig();
        rs2ConfigEnableStream(config, Stream.RS2_STREAM_DEPTH.ordinal(), 0,640, 480, Format.RS2_FORMAT_Y16.ordinal(), 30);
        rs2PipelineStartWithConfig(pipeline, config);


        while (true) {
            long frames = rs2PipelineWaitForFrames(pipeline, 5000);
            int frameCount = rs2EmbeddedFramesCount(frames);

            System.out.println("got frames: " + frameCount);

            for (int i = 0; i < frameCount; ++i) {
                long frame = rs2ExtractFrame(frames, i);
                int width = rs2GetFrameWidth(frame);
                int height = rs2GetFrameHeight(frame);

                System.out.println("width: " + width + " height: " + height);
                rs2ReleaseFrame(frame);

            }
            rs2ReleaseFrame(frames);

        }




    }
}

