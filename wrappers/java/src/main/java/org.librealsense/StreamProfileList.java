package org.librealsense;

public class StreamProfileList {

    long instance;

    protected StreamProfileList(long instance) {
        this.instance = instance;
    }

    public int getSize() {
        return Native.rs2GetStreamProfileCount(instance);
    }

    public StreamProfile get(int index) {
        return new StreamProfile(Native.rs2GetStreamProfile(instance, index));
    }

    public void destroy() {
        Native.rs2DeleteStreamProfilesList(instance);
    }
}