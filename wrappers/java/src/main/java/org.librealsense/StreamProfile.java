package org.librealsense;

class StreamProfile {

    long streamProfile;

    protected  StreamProfile(long streamProfile) {
        this.streamProfile = streamProfile;
    }

    public Intrinsics getVideoStreamIntrinsics() {
        return new Intrinsics(Native.rs2GetVideoStreamIntrinsics(streamProfile));
    }
}