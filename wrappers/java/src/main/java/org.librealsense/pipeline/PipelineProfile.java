package org.librealsense.pipeline;

import org.librealsense.Native;
import org.librealsense.streamprofiles.StreamProfileList;

public class PipelineProfile {

    protected long instance;

    public PipelineProfile(long instance) {
        this.instance = instance;
    }

    public StreamProfileList getStreams() {
        return new StreamProfileList(Native.rs2PipelineProfileGetStreams(instance));
    }
}