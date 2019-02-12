package org.librealsense.processing;

import org.librealsense.frames.Frame;
import org.librealsense.Native;
import org.librealsense.frames.Points;

public class PointCloud extends ProcessingBlock {

    public PointCloud()
    {
        instance = Native.rs2CreatePointCloud();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }

    public Points process(Frame original)
    {
        return new Points(super.process(original).getInstance());
    }
}
