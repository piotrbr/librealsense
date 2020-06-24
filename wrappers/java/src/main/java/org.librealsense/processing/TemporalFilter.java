package org.librealsense.processing;

import org.librealsense.Native;

public class TemporalFilter extends ProcessingBlock implements DepthFrameFilter {

    public TemporalFilter() {
        instance = Native.rs2CreateTemporalFilter();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}
