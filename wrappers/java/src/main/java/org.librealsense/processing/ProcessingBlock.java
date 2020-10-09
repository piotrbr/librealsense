package org.librealsense.processing;

import org.librealsense.frames.Frame;
import org.librealsense.frames.FrameQueue;
import org.librealsense.Native;

public abstract class ProcessingBlock {
    long instance;
    FrameQueue queue = new FrameQueue(1);

    public Frame process(Frame original)
    {
        Native.rs2FrameAddRef(original.getInstance());
        Native.rs2ProcessFrame(this.instance, original.getInstance());

        Frame result = queue.pollForFrame();
        if(result != null)
            return result;

        return original;
    }

    public void delete() {
        Native.rs2DeleteProcessingBlock(instance);
    }

    public long getInstance() {
        return instance;
    }
}
