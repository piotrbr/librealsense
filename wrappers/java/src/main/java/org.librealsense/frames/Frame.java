package org.librealsense.frames;

import org.librealsense.Native;
import org.librealsense.streamprofiles.StreamProfile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Frame {

    protected long instance;

    public Frame(long instance) {
        this.instance = instance;
    }

    public ByteBuffer getFrameData() {
        ByteBuffer buffer = Native.rs2GetFrameData(instance);
        buffer.order(ByteOrder.nativeOrder());
        return buffer;
    }

    public void release() {
        Native.rs2ReleaseFrame(instance);
    }

    public int getWidth() {
        return Native.rs2GetFrameWidth(instance);
    }

    public int getHeight() {
        return Native.rs2GetFrameHeight(instance);
    }

    public int getStrideInBytes() {
        return Native.rs2GetFrameStrideInBytes(instance);
    }

    public boolean isExtendableTo(Native.Extension extension) {
        return 1 == Native.rs2IsFrameExtendableTo(instance, extension.ordinal());
    }

    public StreamProfile getStreamProfile() {
        long streamProfilePtr = Native.rs2GetFrameStreamProfile(instance);
        return new StreamProfile(streamProfilePtr);
    }

    public long getFrameNumber()
    {
        return Native.rs2GetFrameNumber(instance);
    }

    public long getInstance() {
        return instance;
    }

    public float depthFrameGetDistance(int x, int y) {
        return Native.rs2DepthFrameGetDistance(instance, x, y);
    }
}