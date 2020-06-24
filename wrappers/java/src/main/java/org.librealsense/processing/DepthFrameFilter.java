package org.librealsense.processing;

import org.librealsense.Native;
import org.librealsense.frames.Frame;

public interface DepthFrameFilter {
    long getInstance();
    Frame process(Frame original);
    default void setOption(Native.Option option, float value) {
        Native.rs2SetOption(getInstance(), option.ordinal(), value);
    }
}
