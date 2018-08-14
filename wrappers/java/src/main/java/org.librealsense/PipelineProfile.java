package org.librealsense;

public class PipelineProfile {

    protected long pipelineProfile;

    protected PipelineProfile(long pipelineProfile) {
        this.pipelineProfile = pipelineProfile;
    }

    public StreamProfileList getStreams() {
        return new StreamProfileList(Native.rs2PipelineProfileGetStreams(pipelineProfile));
    }
}