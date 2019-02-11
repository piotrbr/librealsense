package org.librealsense;

public class Pipeline {

    private long instance;

    protected Pipeline(long instance) {
        this.instance = instance;
    }

    public PipelineProfile startWithConfig(Config config) {
        return new PipelineProfile(Native.rs2PipelineStartWithConfig(instance, config.instance));
    }

    public FrameList waitForFrames(int timeout) {
        return new FrameList(Native.rs2PipelineWaitForFrames(instance, timeout));
    }

    public void stop()
    {
        Native.rs2PipelineStop(instance);
    }
}