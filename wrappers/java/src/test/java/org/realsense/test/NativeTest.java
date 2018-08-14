package org.realsense.test;

import org.librealsense.Native;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static org.realsense.test.TestUtil.loadLibraries;

public class NativeTest {
    public static void main(String[] args) {
        loadLibraries();

        long context = Native.rs2CreateContext(0);
        long deviceList = Native.rs2QueryDevices(context);
        int deviceCount = Native.rs2GetDeviceCount(deviceList);

        System.out.println("devices: " + deviceCount);

        for (int i = 0; i < 1; ++i) {
            long device = Native.rs2CreateDevice(deviceList, i);
            System.out.println("supports name: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_NAME));
            System.out.println("name:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_NAME));

            System.out.println("supports serial : " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_SERIAL_NUMBER));
            System.out.println("serial:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_SERIAL_NUMBER));

            System.out.println("supports firmware version: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_FIRMWARE_VERSION));
            System.out.println("firmware version:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_FIRMWARE_VERSION));

            System.out.println("supports phyisical port: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_PHYSICAL_PORT));
            System.out.println("physical port:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_PHYSICAL_PORT));

            System.out.println("supports debug op code: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_DEBUG_OP_CODE));
            System.out.println("debug op code:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_DEBUG_OP_CODE));

            System.out.println("supports advanced mode: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_ADVANCED_MODE));
            System.out.println("advanced mode:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_ADVANCED_MODE));

            System.out.println("supports camera locked: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_CAMERA_LOCKED));
            System.out.println("camera locked:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_CAMERA_LOCKED));

            System.out.println("supports usb type descriptor: " + Native.rs2SupportsDeviceInfo(device, Native.RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
            System.out.println("usb type descriptor:" + Native.rs2GetDeviceInfo(device, Native.RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
        }


        long pipeline = Native.rs2CreatePipeline(context);
        long config = Native.rs2CreateConfig();
        Native.rs2ConfigEnableStream(config, Native.Stream.RS2_STREAM_DEPTH.ordinal(), 0, 640, 480, Native.Format.RS2_FORMAT_Z16.ordinal(), 30);
        Native.rs2PipelineStartWithConfig(pipeline, config);


        while (true) {
            long frames = Native.rs2PipelineWaitForFrames(pipeline, 5000);
            int frameCount = Native.rs2EmbeddedFramesCount(frames);

            for (int i = 0; i < frameCount; ++i) {
                long frame = Native.rs2ExtractFrame(frames, i);
                int width = Native.rs2GetFrameWidth(frame);
                int height = Native.rs2GetFrameHeight(frame);
                float depth = Native.rs2DepthFrameGetDistance(frame, 320, 240);
                System.out.println("depth:" + depth);

                ByteBuffer buffer = Native.rs2GetFrameData(frame);
                CharBuffer cb = buffer.asCharBuffer();


                int cd = cb.get(240 * width + 320);
                System.out.println(cd);

                Native.rs2ReleaseFrame(frame);
            }
            if (frameCount > 0) {
                Native.rs2ReleaseFrame(frames);
            }
        }
    }
}
