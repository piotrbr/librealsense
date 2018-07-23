package org.librealsense;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Frame {

    protected long frame;

    protected Frame(long frame) {
        this.frame = frame;
    }

    public ByteBuffer getFrameData() {
        ByteBuffer buffer = Native.rs2GetFrameData(frame);
        buffer.order(ByteOrder.nativeOrder());
        return buffer;
    }

    public void release() {
        Native.rs2ReleaseFrame(frame);
    }

    public int getWidth() {
        return Native.rs2GetFrameWidth(frame);
    }

    public int getHeight() {
        return Native.rs2GetFrameHeight(frame);
    }

    public int getStrideInBytes() {
        return Native.rs2GetFrameStrideInBytes(frame);
    }

    public boolean isExtendableTo(Native.Extension extension) {
        return 1 == Native.rs2IsFrameExtendableTo(frame, extension.ordinal());
    }

    public long getPtr() {
        return frame;
    }
}