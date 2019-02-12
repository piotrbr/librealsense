package org.librealsense.streamprofiles;

import org.librealsense.types.Intrinsics;
import org.librealsense.Native;

public class StreamProfile {

    long instance;

    Native.Stream stream;

    Native.Format format;

    int index;

    int uniqueId;

    int frameRate;

    public StreamProfile(long instance) {
        this.instance = instance;
    }

    public Intrinsics getVideoStreamIntrinsics() {
        Intrinsics intrinsics = new Intrinsics();
        Native.rs2GetVideoStreamIntrinsics(instance, intrinsics);
        return intrinsics;
    }

    public void destroy()
    {
        Native.rs2DeleteStreamProfile(instance);
    }

    public void getProfileData()
    {
        StreamProfileData profileData = new StreamProfileData();
        Native.rs2GetStreamProfileData(instance, profileData);

        stream = Native.Stream.values()[profileData.nativeStreamIndex];
        format = Native.Format.values()[profileData.nativeFormatIndex];

        index = profileData.index;
        uniqueId = profileData.uniqueId;
        frameRate = profileData.frameRate;
    }

    public Native.Stream getStream() {
        return stream;
    }

    public Native.Format getFormat() {
        return format;
    }

    public int getIndex() {
        return index;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public int getFrameRate() {
        return frameRate;
    }
}