package org.librealsense;

import org.bven.jni.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Native {

    static {

        ArchLoader.load("realsense2", "/lib/org.librealsense", Native.class);
        ArchLoader.load("native", "/lib/org.librealsense", Native.class);
//        ArchLoader.load(Native.class);
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

    public static enum Extension {
        RS2_EXTENSION_UNKNOWN,
        RS2_EXTENSION_DEBUG,
        RS2_EXTENSION_INFO,
        RS2_EXTENSION_MOTION,
        RS2_EXTENSION_OPTIONS,
        RS2_EXTENSION_VIDEO,
        RS2_EXTENSION_ROI,
        RS2_EXTENSION_DEPTH_SENSOR,
        RS2_EXTENSION_VIDEO_FRAME,
        RS2_EXTENSION_MOTION_FRAME,
        RS2_EXTENSION_COMPOSITE_FRAME,
        RS2_EXTENSION_POINTS,
        RS2_EXTENSION_DEPTH_FRAME,
        RS2_EXTENSION_ADVANCED_MODE,
        RS2_EXTENSION_RECORD,
        RS2_EXTENSION_VIDEO_PROFILE,
        RS2_EXTENSION_PLAYBACK,
        RS2_EXTENSION_DEPTH_STEREO_SENSOR,
        RS2_EXTENSION_DISPARITY_FRAME,
        RS2_EXTENSION_MOTION_PROFILE,
        RS2_EXTENSION_POSE_FRAME,
        RS2_EXTENSION_POSE_PROFILE,
        RS2_EXTENSION_TM2,
        RS2_EXTENSION_SOFTWARE_DEVICE,
        RS2_EXTENSION_SOFTWARE_SENSOR,
        RS2_EXTENSION_COUNT
    }

    public static enum Option {
        RS2_OPTION_BACKLIGHT_COMPENSATION                     , /**< Enable / disable color backlight compensation*/
        RS2_OPTION_BRIGHTNESS                                 , /**< Color image brightness*/
        RS2_OPTION_CONTRAST                                   , /**< Color image contrast*/
        RS2_OPTION_EXPOSURE                                   , /**< Controls exposure time of color camera. Setting any value will disable auto exposure*/
        RS2_OPTION_GAIN                                       , /**< Color image gain*/
        RS2_OPTION_GAMMA                                      , /**< Color image gamma setting*/
        RS2_OPTION_HUE                                        , /**< Color image hue*/
        RS2_OPTION_SATURATION                                 , /**< Color image saturation setting*/
        RS2_OPTION_SHARPNESS                                  , /**< Color image sharpness setting*/
        RS2_OPTION_WHITE_BALANCE                              , /**< Controls white balance of color image. Setting any value will disable auto white balance*/
        RS2_OPTION_ENABLE_AUTO_EXPOSURE                       , /**< Enable / disable color image auto-exposure*/
        RS2_OPTION_ENABLE_AUTO_WHITE_BALANCE                  , /**< Enable / disable color image auto-white-balance*/
        RS2_OPTION_VISUAL_PRESET                              , /**< Provide access to several recommend sets of option presets for the depth camera */
        RS2_OPTION_LASER_POWER                                , /**< Power of the F200 / SR300 projector, with 0 meaning projector off*/
        RS2_OPTION_ACCURACY                                   , /**< Set the number of patterns projected per frame. The higher the accuracy value the more patterns projected. Increasing the number of patterns help to achieve better accuracy. Note that this control is affecting the Depth FPS */
        RS2_OPTION_MOTION_RANGE                               , /**< Motion vs. Range trade-off, with lower values allowing for better motion sensitivity and higher values allowing for better depth range*/
        RS2_OPTION_FILTER_OPTION                              , /**< Set the filter to apply to each depth frame. Each one of the filter is optimized per the application requirements*/
        RS2_OPTION_CONFIDENCE_THRESHOLD                       , /**< The confidence level threshold used by the Depth algorithm pipe to set whether a pixel will get a valid range or will be marked with invalid range*/
        RS2_OPTION_EMITTER_ENABLED                            , /**< Laser Emitter enabled */
        RS2_OPTION_FRAMES_QUEUE_SIZE                          , /**< Number of frames the user is allowed to keep per stream. Trying to hold-on to more frames will cause frame-drops.*/
        RS2_OPTION_TOTAL_FRAME_DROPS                          , /**< Total number of detected frame drops from all streams */
        RS2_OPTION_AUTO_EXPOSURE_MODE                         , /**< Auto-Exposure modes: Static, Anti-Flicker and Hybrid */
        RS2_OPTION_POWER_LINE_FREQUENCY                       , /**< Power Line Frequency control for anti-flickering Off/50Hz/60Hz/Auto */
        RS2_OPTION_ASIC_TEMPERATURE                           , /**< Current Asic Temperature */
        RS2_OPTION_ERROR_POLLING_ENABLED                      , /**< disable error handling */
        RS2_OPTION_PROJECTOR_TEMPERATURE                      , /**< Current Projector Temperature */
        RS2_OPTION_OUTPUT_TRIGGER_ENABLED                     , /**< Enable / disable trigger to be outputed from the camera to any external device on every depth frame */
        RS2_OPTION_MOTION_MODULE_TEMPERATURE                  , /**< Current Motion-Module Temperature */
        RS2_OPTION_DEPTH_UNITS                                , /**< Number of meters represented by a single depth unit */
        RS2_OPTION_ENABLE_MOTION_CORRECTION                   , /**< Enable/Disable automatic correction of the motion data */
        RS2_OPTION_AUTO_EXPOSURE_PRIORITY                     , /**< Allows sensor to dynamically ajust the frame rate depending on lighting conditions */
        RS2_OPTION_COLOR_SCHEME                               , /**< Color scheme for data visualization */
        RS2_OPTION_HISTOGRAM_EQUALIZATION_ENABLED             , /**< Perform histogram equalization post-processing on the depth data */
        RS2_OPTION_MIN_DISTANCE                               , /**< Minimal distance to the target */
        RS2_OPTION_MAX_DISTANCE                               , /**< Maximum distance to the target */
        RS2_OPTION_TEXTURE_SOURCE                             , /**< Texture mapping stream unique ID */
        RS2_OPTION_FILTER_MAGNITUDE                           , /**< The 2D-filter effect. The specific interpretation is given within the context of the filter */
        RS2_OPTION_FILTER_SMOOTH_ALPHA                        , /**< 2D-filter parameter controls the weight/radius for smoothing.*/
        RS2_OPTION_FILTER_SMOOTH_DELTA                        , /**< 2D-filter range/validity threshold*/
        RS2_OPTION_HOLES_FILL                                 , /**< Enhance depth data post-processing with holes filling where appropriate*/
        RS2_OPTION_STEREO_BASELINE                            , /**< The distance in mm between the first and the second imagers in stereo-based depth cameras*/
        RS2_OPTION_AUTO_EXPOSURE_CONVERGE_STEP                , /**< Allows dynamically ajust the converge step value of the target exposure in Auto-Exposure algorithm*/
        RS2_OPTION_COUNT                                        /**< Number of enumeration values. Not a valid input: intended to be used in for-loops. */

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

    native static ByteBuffer rs2GetFrameData(long frame);



    public static void main(String[] args) {
        Native.helloThere();
        long context = rs2CreateContext(0);
        long deviceList = rs2QueryDevices(context);
        int deviceCount = rs2GetDeviceCount(deviceList);

        System.out.println("devices: " + deviceCount);

        for (int i = 0; i < 1; ++i) {
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
            System.out.println("debug op code:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_DEBUG_OP_CODE));

            System.out.println("supports advanced mode: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_ADVANCED_MODE));
            System.out.println("advanced mode:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_ADVANCED_MODE));

            System.out.println("supports camera locked: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_CAMERA_LOCKED));
            System.out.println("camera locked:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_CAMERA_LOCKED));

            System.out.println("supports usb type descriptor: " + rs2SupportsDeviceInfo(device, RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
            System.out.println("usb type descriptor:" + rs2GetDeviceInfo(device, RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR));
        }


        long pipeline = rs2CreatePipeline(context);
        long config = rs2CreateConfig();
        rs2ConfigEnableStream(config, Stream.RS2_STREAM_DEPTH.ordinal(), 0,640, 480, Format.RS2_FORMAT_Z16.ordinal(), 30);
        rs2PipelineStartWithConfig(pipeline, config);



        while (true) {
            long frames = rs2PipelineWaitForFrames(pipeline, 5000);
            int frameCount = rs2EmbeddedFramesCount(frames);

            for (int i = 0; i < frameCount; ++i) {
                long frame = rs2ExtractFrame(frames, i);
                int width = rs2GetFrameWidth(frame);
                int height = rs2GetFrameHeight(frame);
                float depth = rs2DepthFrameGetDistance(frame, 320, 240);
                System.out.println("depth:" + depth);

                ByteBuffer buffer = rs2GetFrameData(frame);
                CharBuffer cb = buffer.asCharBuffer();


                int cd = cb.get(240 * width + 320);
                System.out.println(cd);

                rs2ReleaseFrame(frame);
            }
            if (frameCount > 0) {
                rs2ReleaseFrame(frames);
            }
        }
    }
}

