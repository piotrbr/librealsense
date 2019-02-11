package org.librealsense;

import org.librealsense.Frame;
import org.librealsense.FrameList;
import org.librealsense.Native;

public abstract class ProcessingBlock {
    long instance;
    FrameQueue queue = new FrameQueue(1);

    public Frame process(Frame original)
    {
        Native.rs2FrameAddRef(original.instance);
        Native.rs2ProcessFrame(this.instance, original.instance);

        Frame result = queue.pollForFrame();
        if(result != null)
            return result;

        return original;
    }
}
