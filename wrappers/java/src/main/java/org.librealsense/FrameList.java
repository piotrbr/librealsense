package org.librealsense;

public class FrameList {

    protected long frameList;

    protected FrameList(long frameList) {
        this.frameList = frameList;
    }

    public int frameCount() {
        return Native.rs2EmbeddedFramesCount(frameList);
    }

    public Frame frame(int index) {
        return new Frame(Native.rs2ExtractFrame(frameList, index));
    }

    public void release() {
        Native.rs2ReleaseFrame(frameList);
    }

    public long getPtr() {
        return frameList;
    }
}