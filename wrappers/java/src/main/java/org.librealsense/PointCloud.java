package org.librealsense;

public class PointCloud extends ProcessingBlock {

    public PointCloud()
    {
        instance = Native.rs2CreatePointCloud();
        Native.rs2StartProcessingQueue(instance, queue.instance);
    }

    public Points process(Frame original)
    {
        return new Points(super.process(original).instance);
    }
}
