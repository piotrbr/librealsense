package ch.bildspur.rs.DepthMapCloud;

import ch.bildspur.rs.P5RealSense;
import org.librealsense.Context;
import org.librealsense.Native;
import org.librealsense.devices.DeviceList;
import org.librealsense.frames.Frame;
import org.librealsense.frames.FrameList;
import org.librealsense.pipeline.Config;
import org.librealsense.pipeline.Pipeline;
import org.librealsense.processing.Align;
import org.librealsense.processing.Colorizer;
import org.librealsense.streamprofiles.StreamProfile;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class DepthMapCloudDemo extends PApplet {
    Context context;
    Pipeline pipeline;
    Colorizer colorizer;
    Align align;

    int inputWidth = 640;
    int inputHeight = 480;
    int frameRate = 30;

    private PImage depthImage;
    private PImage colorImage;
    private PImage leftIRImage;
    private PImage rightIRImage;
    private char[] depthBuffer;

    @Override
    public void settings() {
        size(inputWidth * 2, inputHeight * 2, P2D);
    }

    @Override
    public void setup() {
        // create images
        this.depthImage = new PImage(inputWidth, inputHeight, PConstants.RGB);
        this.colorImage = new PImage(inputWidth, inputHeight, PConstants.RGB);
        this.leftIRImage = new PImage(inputWidth, inputHeight, PConstants.RGB);
        this.rightIRImage = new PImage(inputWidth, inputHeight, PConstants.RGB);

        // create buffer
        this.depthBuffer = new char[inputWidth * inputHeight];

        // start device
        context = Context.create();

        // check if device is online
        DeviceList devices = context.queryDevices();
        if (devices.getDeviceCount() == 0) {
            System.out.println("no device available!");
            exit();
        }

        // create pipeline
        pipeline = context.createPipeline();
        colorizer = new Colorizer();
        align = new Align(Native.Stream.RS2_STREAM_COLOR);

        Config config = Config.create();
        config.enableDevice(devices.getDevices().get(0));

        // enable all streams
        config.enableStream(Native.Stream.RS2_STREAM_DEPTH,
                0,
                inputWidth,
                inputHeight,
                Native.Format.RS2_FORMAT_Z16,
                frameRate);


        config.enableStream(Native.Stream.RS2_STREAM_COLOR,
                0,
                inputWidth,
                inputHeight,
                Native.Format.RS2_FORMAT_RGB8,
                frameRate);

        config.enableStream(Native.Stream.RS2_STREAM_INFRARED,
                1,
                inputWidth,
                inputHeight,
                Native.Format.RS2_FORMAT_Y8,
                frameRate);

        config.enableStream(Native.Stream.RS2_STREAM_INFRARED,
                2,
                inputWidth,
                inputHeight,
                Native.Format.RS2_FORMAT_Y8,
                frameRate);

        // start pipeline
        pipeline.startWithConfig(config);
    }

    @Override
    public void draw() {
        background(0);

        // read frames
        FrameList frames = pipeline.waitForFrames(5000);
        Frame compositeFrame = frames.asFrame();
        Frame aligned = align.process(compositeFrame);
        FrameList alignedFrames = FrameList.fromFrame(aligned);

        for (int i = 0; i < alignedFrames.frameCount(); i++) {
            Frame frame = alignedFrames.frame(i);

            StreamProfile profile = frame.getStreamProfile();
            profile.getProfileData();

            // depth stream
            if (profile.getStream() == Native.Stream.RS2_STREAM_DEPTH) {
                P5RealSense.readDepthBuffer(frame, depthBuffer);

                // check if colorized frame is available
                Frame colorizedFrame = colorizer.process(frame);

                P5RealSense.readColorImage(colorizedFrame, depthImage);

                colorizedFrame.release();
            }

            // color stream
            if(profile.getStream() == Native.Stream.RS2_STREAM_COLOR) {
                P5RealSense.readColorImage(frame, colorImage);
            }

            // infrared stream
            if(profile.getStream() == Native.Stream.RS2_STREAM_INFRARED) {
                if(profile.getIndex() == 1)
                    P5RealSense.readIRImage(frame, leftIRImage);
                else
                    P5RealSense.readIRImage(frame, rightIRImage);
            }

            frame.release();
        }
        frames.release();
        alignedFrames.release();
        compositeFrame.release();
        aligned.release();

        // display streams
        image(depthImage, 0, 0);
        image(colorImage, inputWidth, 0);
        image(leftIRImage, 0, inputHeight);
        image(rightIRImage, inputWidth, inputHeight);

        surface.setTitle("FPS: " + round(frameRate));
    }

    @Override
    public void stop() {
        pipeline.stop();
        context.delete();
    }

    public static void main(String[] args) {
        Native.loadNativeLibraries("libs");
        new DepthMapCloudDemo().runSketch();
    }
}
