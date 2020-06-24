package org.librealsense.processing;

import org.librealsense.Native;

public class DecimationFilter extends ProcessingBlock implements DepthFrameFilter {

    public DecimationFilter() {
        instance = Native.rs2CreateDecimationFilter();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}
