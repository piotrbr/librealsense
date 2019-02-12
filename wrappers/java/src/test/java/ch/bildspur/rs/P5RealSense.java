package ch.bildspur.rs;

import org.librealsense.frames.Frame;
import processing.core.PImage;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class P5RealSense {
    public static void readIRImage(Frame frame, PImage irImage)
    {
        ByteBuffer buffer = frame.getFrameData();
        irImage.loadPixels();

        for(int i = 0; i < irImage.width * irImage.height; i++)
        {
            int irvalue = buffer.get(i) & 0xFF;
            irImage.pixels[i] = toColor(irvalue);
        }

        irImage.updatePixels();
    }

    public static void readDepthBuffer(Frame frame, char[] depthBuffer) {
        CharBuffer buffer = frame.getFrameData().asCharBuffer();
        buffer.get(depthBuffer);
    }

    public static void readColorImage(Frame frame, PImage colorImage) {
        ByteBuffer buffer = frame.getFrameData();
        colorImage.loadPixels();

        for (int i = 0; i < frame.getStrideInBytes() * colorImage.height; i += 3)
        {
            colorImage.pixels[i / 3] = toColor(buffer.get(i) & 0xFF, buffer.get(i + 1) & 0xFF, buffer.get(i + 2) & 0xFF);
        }

        colorImage.updatePixels();
    }

    public static int toColor(int gray)
    {
        return toColor(gray, gray, gray);
    }

    public static int toColor(int red, int green, int blue)
    {
        return -16777216 | red << 16 | green << 8 | blue;
    }
}
