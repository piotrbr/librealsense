package org.librealsense.processing;

import org.librealsense.Native;

public class Align extends ProcessingBlock {
    public Align(Native.Stream stream)
    {
        instance = Native.rs2CreateAlign(stream.ordinal());
        Native.rs2StartProcessingQueue(instance, queue.getInstance());
    }
}