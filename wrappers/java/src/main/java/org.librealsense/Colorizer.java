package org.librealsense;

public class Colorizer extends ProcessingBlock {
    public Colorizer()
    {
        instance = Native.rs2CreateColorizer();
        Native.rs2StartProcessingQueue(instance, queue.instance);
    }
}
