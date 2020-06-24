package org.librealsense.processing;

import org.librealsense.Native;

public class HoleFillingFilter extends ProcessingBlock implements DepthFrameFilter {
    public HoleFillingFilter() {
        instance = Native.rs2CreateHoleFillingFilter();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}
