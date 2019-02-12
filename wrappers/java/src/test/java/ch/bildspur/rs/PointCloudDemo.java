package ch.bildspur.rs;

import org.librealsense.Context;
import org.librealsense.Native;
import org.librealsense.devices.DeviceList;
import org.librealsense.frames.Frame;
import org.librealsense.frames.FrameList;
import org.librealsense.frames.Points;
import org.librealsense.pipeline.Config;
import org.librealsense.pipeline.Pipeline;
import org.librealsense.processing.Colorizer;
import org.librealsense.processing.PointCloud;
import org.librealsense.streamprofiles.StreamProfile;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class PointCloudDemo extends PApplet {
    Context context;
    Pipeline pipeline;
    PointCloud pointCloud;

    int inputWidth = 640;
    int inputHeight = 480;
    int frameRate = 30;

    FloatBuffer vertexBuffer;

    @Override
    public void settings() {
        size(inputWidth, inputHeight, P3D);
    }

    @Override
    public void setup() {
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
        pointCloud = new PointCloud();

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

        // start pipeline
        pipeline.startWithConfig(config);
    }

    @Override
    public void draw() {
        background(128);

        // read frames
        FrameList frames = pipeline.waitForFrames(5000);

        for (int i = 0; i < frames.frameCount(); i++) {
            Frame frame = frames.frame(i);

            StreamProfile profile = frame.getStreamProfile();
            profile.getProfileData();

            // depth stream
            if (profile.getStream() == Native.Stream.RS2_STREAM_DEPTH) {
                // check if pointcloud frame is available
                Points points = pointCloud.process(frame);

                if (points != null) {
                    // do something with points
                    println("Points: " + points.getCount());
                    ByteBuffer rawVertices = points.rawVertexBuffer();
                    rawVertices.order(ByteOrder.nativeOrder());

                    vertexBuffer = rawVertices.asFloatBuffer();

                    points.release();
                }
            }

            frame.release();
        }
        frames.release();

        println("Buffer: " + vertexBuffer.capacity());

        surface.setTitle("FPS: " + round(frameRate));
    }

    @Override
    public void stop() {
        pipeline.stop();
        context.delete();
    }

    public static void main(String[] args) {
        Native.loadNativeLibraries("libs");
        new PointCloudDemo().runSketch();
    }
}
