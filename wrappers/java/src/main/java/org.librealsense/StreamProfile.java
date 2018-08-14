package org.librealsense;

public class StreamProfile {

    long instance;

    protected StreamProfile(long instance) {
        this.instance = instance;
    }

    public Intrinsics getVideoStreamIntrinsics() {
        Intrinsics intrinsics = new Intrinsics();
        Native.rs2GetVideoStreamIntrinsics(instance, intrinsics);
        return intrinsics;
    }
}