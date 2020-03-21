package org.librealsense.pipeline;

import org.librealsense.Native;
import org.librealsense.frames.FrameList;

public class Pipeline {

    private long instance;

    public Pipeline(long instance) {
        this.instance = instance;
    }

    public PipelineProfile startWithConfig(Config config) {
        return new PipelineProfile(Native.rs2PipelineStartWithConfig(instance, config.instance));
    }

    public FrameList waitForFrames(int timeout) {
        return new FrameList(Native.rs2PipelineWaitForFrames(instance, timeout));
    }

    public FrameList pollForFrames() {
        long instance = Native.rs2PipelinePollForFrames(this.instance);
        return instance != -1 ? new FrameList(instance) : null;
    }

    public PipelineProfile getActiveProfile() {
        return new PipelineProfile(Native.rs2PipelineGetActiveProfile(instance));
    }

    public void stop()
    {
        Native.rs2PipelineStop(instance);
    }

    public void delete()
    {
        Native.rs2DeletePipeline(instance);
    }
}