package org.librealsense;

public class Align extends ProcessingBlock {
    public Align(Native.Stream stream)
    {
        instance = Native.rs2CreateAlign(stream.ordinal());
        Native.rs2StartProcessingQueue(instance, queue.instance);
    }
}