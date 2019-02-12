package org.librealsense.processing;

import org.librealsense.Native;

public class Colorizer extends ProcessingBlock {
    public Colorizer()
    {
        instance = Native.rs2CreateColorizer();
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}
