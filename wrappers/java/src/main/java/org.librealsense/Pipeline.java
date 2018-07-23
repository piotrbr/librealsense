package org.librealsense;

public class Pipeline {

    private long pipeline;
    protected Pipeline(long pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineProfile startWithConfig(Config config) {
        return new PipelineProfile( Native.rs2PipelineStartWithConfig(pipeline, config.config) );
    }

    public FrameList waitForFrames(int timeout) {
        return new FrameList( Native.rs2PipelineWaitForFrames(pipeline, timeout));
    }

    public long getPtr() {
        return pipeline;
    }
}