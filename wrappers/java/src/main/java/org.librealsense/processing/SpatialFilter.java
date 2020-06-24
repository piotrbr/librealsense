package org.librealsense.processing;

import org.librealsense.Native;

public class SpatialFilter extends ProcessingBlock implements DepthFrameFilter {

    public SpatialFilter() {
        instance = Native.rs2CreateSpatialFilter();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}
