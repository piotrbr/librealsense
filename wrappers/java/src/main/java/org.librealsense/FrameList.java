package org.librealsense;

public class FrameList {

    protected long instance;

    protected FrameList(long instance) {
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
}