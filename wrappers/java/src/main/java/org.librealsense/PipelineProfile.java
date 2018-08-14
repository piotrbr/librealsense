package org.librealsense;

public class PipelineProfile {

    protected long instance;

    protected PipelineProfile(long instance) {
        this.instance = instance;
    }

    public StreamProfileList getStreams() {
        return new StreamProfileList(Native.rs2PipelineProfileGetStreams(instance));
    }
}