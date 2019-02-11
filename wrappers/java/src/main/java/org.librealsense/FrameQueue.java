package org.librealsense;

public class FrameQueue {

    long instance;

    public FrameQueue(int capacity)
    {
        this.instance = Native.rs2CreateFrameQueue(capacity);
    }

    public Frame pollForFrame()
    {
        long ptr = Native.rs2PollForFrame(instance);

        if(ptr == -1)
            return null;

        return new Frame(ptr);
    }
}
