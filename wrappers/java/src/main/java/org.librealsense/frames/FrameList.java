package org.librealsense.frames;

import org.librealsense.Native;

public class FrameList {

    protected long instance;

    public  FrameList(long instance) {
        this.instance = instance;
    }

    public int frameCount() {
        return Native.rs2EmbeddedFramesCount(instance);
    }

    public Frame frame(int index) {
        return new Frame(Native.rs2ExtractFrame(instance, index));
    }

    public void release() {
        Native.rs2ReleaseFrame(instance);
    }

    public Frame asFrame()
    {
        Native.rs2FrameAddRef(instance);
        return new Frame(instance);
    }

    public static FrameList fromFrame(Frame frame)
    {
        Native.rs2FrameAddRef(frame.instance);
        return new FrameList(frame.instance);
    }
}