package org.librealsense;

public class StreamProfile {

    long streamProfile;

    protected StreamProfile(long streamProfile) {
        this.streamProfile = streamProfile;
    }

    public Intrinsics getVideoStreamIntrinsics() {
        Intrinsics intrinsics = new Intrinsics();
        Native.rs2GetVideoStreamIntrinsics(streamProfile, intrinsics);
        return intrinsics;
    }
}