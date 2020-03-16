package org.librealsense;

import org.librealsense.streamprofiles.StreamProfileData;
import org.librealsense.types.Intrinsics;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class Native {
    public static int RS2_CAMERA_INFO_NAME = 0;
    public static int RS2_CAMERA_INFO_SERIAL_NUMBER = 1;
    public static int RS2_CAMERA_INFO_FIRMWARE_VERSION = 2;
    public static int RS2_CAMERA_INFO_PHYSICAL_PORT = 3;
    public static int RS2_CAMERA_INFO_DEBUG_OP_CODE = 4;
    public static int RS2_CAMERA_INFO_ADVANCED_MODE = 5;
    public static int RS2_CAMERA_INFO_CAMERA_LOCKED = 6;
    public static int RS2_CAMERA_INFO_USB_TYPE_DESCRIPTOR = 7;
    public static int RS2_CAMERA_INFO_COUNT = 8;
    public static int RS2_DISTORTION_NONE = 0;
    public static int RS2_DISTORTION_MODIFIED_BROWN_CONRADY = 1;
    public static int RS2_DISTORTION_INVERSE_BROWN_CONRADY = 2;
    public static int RS2_FTHETA = 3;
    public static int RS2_BROWN_CONRADY = 4;

    static void loadLibrary(String resource, String target) {
        InputStream stream = Native.class.getResourceAsStream(resource);

        try {
            File tmpDir = new File(System.getProperty("java.io.tmpdir"));
            File jinectDir = new File(tmpDir, "librealsense-jvm");
            jinectDir.mkdirs();

            File jinectLib = new File(jinectDir, target);

            if (!jinectLib.exists()) {
                OutputStream out = new FileOutputStream(jinectLib);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = stream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.close();
            }
            System.load(jinectLib.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the native libraries for the specific os directly out of the jar file.
     */
    public static void loadNativeLibraries() {
        // check if development mode
        boolean isRSDEV = System.getProperty("rsdev") != null && System.getProperty("rsdev").equals("1");

        // load native os specific libraries
        String os = System.getProperty("os.name").toLowerCase();

        if(!isRSDEV) {
            if (os.contains("win")) {
                loadLibrary("/lib/org.librealsense/windows-x64/realsense2.dll", "realsense2.dll");
                loadLibrary("/lib/org.librealsense/windows-x64/native.dll", "native.dll");
            } else if (os.contains("mac")) {
                loadLibrary("/lib/org.librealsense/osx-x64/librealsense2.dylib", "librealsense2.dylib");
                loadLibrary("/lib/org.librealsense/osx-x64/libnative.dylib", "libnative.dylib");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                // TODO: implement unix support
            } else {
                // Operating System not supported!
            }
        }
    }

    /**
     * Specify the path to the directory from where to load the native libraries from.
     * @param libPath Path to the directory where the native realsense lib and wrapper are stored.
     */
    public static void loadNativeLibraries(String libPath) {
        // load native os specific libraries
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            System.load(Paths.get(libPath, "realsense2.dll").toAbsolutePath().toString());
            System.load(Paths.get(libPath, "rsjvm.dll").toAbsolutePath().toString());
        } else if (os.contains("mac")) {
            System.load(Paths.get(libPath, "librealsense2.dylib").toAbsolutePath().toString());
            System.load(Paths.get(libPath, "librsjvm.dylib").toAbsolutePath().toString());
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            System.load(Paths.get(libPath, "librealsense2.so.2.33.1").toAbsolutePath().toString());
            System.load(Paths.get(libPath, "librsjvm.so").toAbsolutePath().toString());
        } else {
            // Operating System not supported!
            System.out.println("Your os is not supported. Please load the native libraries yourself!");
        }
    }

    public native static long rs2CreateContext(int apiVersion);

    public native static void rs2DeleteContext(long context);

    public native static long rs2QueryDevices(long context);

    public native static int rs2GetDeviceCount(long deviceList);

    public native static long rs2CreateDevice(long deviceList, int deviceIndex);

    public native static void rs2DeleteDevice(long device);

    public native static void rs2HardwareReset(long device);

    public native static int rs2SupportsDeviceInfo(long device, int info);

    public native static String rs2GetDeviceInfo(long device, int info);

    public native static long rs2CreatePipeline(long context);

    public native static long rs2CreateConfig();

    public native static void rs2DeleteConfig(long config);

    public native static void rs2ConfigEnableStream(long config, int stream, int index, int width, int height, int format, int framerate);

    public native static void rs2ConfigEnableDevice(long config, String serial);

    public native static void rs2ConfigEnableRecordToFile(long config, String filename);

    public native static void rs2ConfigEnableDeviceFromFile(long config, String filename);

    public native static void rs2ConfigDisableStream(long config, long stream);

    public native static int rs2ConfigCanResolve(long config, long pipeline);

    public native static long rs2PipelineStartWithConfig(long pipeline, long config);

    public native static void rs2PipelineStop(long pipeline);

    public native static void rs2DeletePipeline(long pipeline);

    public native static long rs2PipelineProfileGetStreams(long pipelineProfile);

    public native static long rs2PipelineWaitForFrames(long pipeline, int timeOut);

    public native static long rs2PipelinePollForFrames(long pipeline);

    public native static int rs2EmbeddedFramesCount(long frames);

    public native static long rs2ExtractFrame(long frames, int index);

    public native static long rs2ReleaseFrame(long frame);

    public native static long rs2GetFrameNumber(long frame);

    public native static long rs2GetFrameStreamProfile(long frame);

    public native static int rs2IsFrameExtendableTo(long frame, int extension);

    public native static int rs2GetFrameWidth(long frame);

    public native static int rs2GetFrameHeight(long frame);

    public native static int rs2GetFrameStrideInBytes(long frame);

    public native static float rs2DepthFrameGetDistance(long frame, int x, int y);

    public native static ByteBuffer rs2GetFrameData(long frame);

    public native static ByteBuffer rs2GetFrameVertices(long frame);

    public native static void rs2ExportToPly(long frame, String fileName, long texture);

    public native static void rs2FrameAddRef(long frame);

    public native static void rs2ProcessFrame(long processingBlock, long frame);

    public native static long rs2PollForFrame(long queue);

    public native static long rs2CreateFrameQueue(int capacity);

    public native static void rs2StartProcessingQueue(long processingBlock, long queue);

    public native static long rs2CreateColorizer();

    public native static long rs2CreatePointCloud();

    public native static long rs2CreateAlign(int stream);

    public native static int rs2GetFramePointsCount(long frame);

    public native static long rs2QuerySensors(long device);

    public native static int rs2GetSensorsCount(long sensorList);

    public native static void rs2DeleteSensorList(long sensorList);

    public native static void rs2DeleteSensor(long sensor);

    public native static long rs2CreateSensor(long sensorList, int index);

    public native static String rs2GetSensorInfo(long sensor, int cameraInfo);

    public native static int rs2SupportsSensorInfo(long sensor, int cameraInfo);

    public native static int rs2IsSensorExtendableTo(long sensor, int extension);

    public native static float rs2GetDepthScale(long sensor);

    public native static float rs2DepthStereoFrameGetBaseline(long frame);

    public native static long rs2GetStreamProfiles(long sensor);

    public native static int rs2GetStreamProfileCount(long streamProfileList);

    public native static void rs2DeleteStreamProfilesList(long streamProfileList);

    public native static long rs2GetStreamProfile(long streamProfileList, int index);

    public native static void rs2DeleteStreamProfile(long streamProfile);

    public native static long rs2GetVideoStreamIntrinsics(long streamProfile, Intrinsics intrinsics);

    public native static void rs2GetStreamProfileData(long streamProfile, StreamProfileData data);

    public native static long rs2DeleteIntrinsics(long intrinsics);


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
        RS2_OPTION_BACKLIGHT_COMPENSATION,
        /**
         * < Enable / disable color backlight compensation
         */
        RS2_OPTION_BRIGHTNESS,
        /**
         * < Color image brightness
         */
        RS2_OPTION_CONTRAST,
        /**
         * < Color image contrast
         */
        RS2_OPTION_EXPOSURE,
        /**
         * < Controls exposure time of color camera. Setting any value will disable auto exposure
         */
        RS2_OPTION_GAIN,
        /**
         * < Color image gain
         */
        RS2_OPTION_GAMMA,
        /**
         * < Color image gamma setting
         */
        RS2_OPTION_HUE,
        /**
         * < Color image hue
         */
        RS2_OPTION_SATURATION,
        /**
         * < Color image saturation setting
         */
        RS2_OPTION_SHARPNESS,
        /**
         * < Color image sharpness setting
         */
        RS2_OPTION_WHITE_BALANCE,
        /**
         * < Controls white balance of color image. Setting any value will disable auto white balance
         */
        RS2_OPTION_ENABLE_AUTO_EXPOSURE,
        /**
         * < Enable / disable color image auto-exposure
         */
        RS2_OPTION_ENABLE_AUTO_WHITE_BALANCE,
        /**
         * < Enable / disable color image auto-white-balance
         */
        RS2_OPTION_VISUAL_PRESET,
        /**
         * < Provide access to several recommend sets of option presets for the depth camera
         */
        RS2_OPTION_LASER_POWER,
        /**
         * < Power of the F200 / SR300 projector, with 0 meaning projector off
         */
        RS2_OPTION_ACCURACY,
        /**
         * < Set the number of patterns projected per instance. The higher the accuracy value the more patterns projected. Increasing the number of patterns help to achieve better accuracy. Note that this control is affecting the Depth FPS
         */
        RS2_OPTION_MOTION_RANGE,
        /**
         * < Motion vs. Range trade-off, with lower values allowing for better motion sensitivity and higher values allowing for better depth range
         */
        RS2_OPTION_FILTER_OPTION,
        /**
         * < Set the filter to apply to each depth instance. Each one of the filter is optimized per the application requirements
         */
        RS2_OPTION_CONFIDENCE_THRESHOLD,
        /**
         * < The confidence level threshold used by the Depth algorithm pipe to set whether a pixel will get a valid range or will be marked with invalid range
         */
        RS2_OPTION_EMITTER_ENABLED,
        /**
         * < Laser Emitter enabled
         */
        RS2_OPTION_FRAMES_QUEUE_SIZE,
        /**
         * < Number of frames the user is allowed to keep per stream. Trying to hold-on to more frames will cause instance-drops.
         */
        RS2_OPTION_TOTAL_FRAME_DROPS,
        /**
         * < Total number of detected instance drops from all streams
         */
        RS2_OPTION_AUTO_EXPOSURE_MODE,
        /**
         * < Auto-Exposure modes: Static, Anti-Flicker and Hybrid
         */
        RS2_OPTION_POWER_LINE_FREQUENCY,
        /**
         * < Power Line Frequency control for anti-flickering Off/50Hz/60Hz/Auto
         */
        RS2_OPTION_ASIC_TEMPERATURE,
        /**
         * < Current Asic Temperature
         */
        RS2_OPTION_ERROR_POLLING_ENABLED,
        /**
         * < disable error handling
         */
        RS2_OPTION_PROJECTOR_TEMPERATURE,
        /**
         * < Current Projector Temperature
         */
        RS2_OPTION_OUTPUT_TRIGGER_ENABLED,
        /**
         * < Enable / disable trigger to be outputed from the camera to any external device on every depth instance
         */
        RS2_OPTION_MOTION_MODULE_TEMPERATURE,
        /**
         * < Current Motion-Module Temperature
         */
        RS2_OPTION_DEPTH_UNITS,
        /**
         * < Number of meters represented by a single depth unit
         */
        RS2_OPTION_ENABLE_MOTION_CORRECTION,
        /**
         * < Enable/Disable automatic correction of the motion data
         */
        RS2_OPTION_AUTO_EXPOSURE_PRIORITY,
        /**
         * < Allows instance to dynamically ajust the instance rate depending on lighting conditions
         */
        RS2_OPTION_COLOR_SCHEME,
        /**
         * < Color scheme for data visualization
         */
        RS2_OPTION_HISTOGRAM_EQUALIZATION_ENABLED,
        /**
         * < Perform histogram equalization post-processing on the depth data
         */
        RS2_OPTION_MIN_DISTANCE,
        /**
         * < Minimal distance to the target
         */
        RS2_OPTION_MAX_DISTANCE,
        /**
         * < Maximum distance to the target
         */
        RS2_OPTION_TEXTURE_SOURCE,
        /**
         * < Texture mapping stream unique ID
         */
        RS2_OPTION_FILTER_MAGNITUDE,
        /**
         * < The 2D-filter effect. The specific interpretation is given within the context of the filter
         */
        RS2_OPTION_FILTER_SMOOTH_ALPHA,
        /**
         * < 2D-filter parameter controls the weight/radius for smoothing.
         */
        RS2_OPTION_FILTER_SMOOTH_DELTA,
        /**
         * < 2D-filter range/validity threshold
         */
        RS2_OPTION_HOLES_FILL,
        /**
         * < Enhance depth data post-processing with holes filling where appropriate
         */
        RS2_OPTION_STEREO_BASELINE,
        /**
         * < The distance in mm between the first and the second imagers in stereo-based depth cameras
         */
        RS2_OPTION_AUTO_EXPOSURE_CONVERGE_STEP,
        /**
         * < Allows dynamically ajust the converge step value of the target exposure in Auto-Exposure algorithm
         */
        RS2_OPTION_COUNT                                        /**< Number of enumeration values. Not a valid input: intended to be used in for-loops. */

    }
}

