package org.librealsense;

class StreamProfileList {

    long streamProfileList;
    protected StreamProfileList(long streamProfileList) {
        this.streamProfileList = streamProfileList;
    }

    public StreamProfile get(int index) {
        return new StreamProfile(Native.rs2GetStreamProfile(streamProfileList, index));
    }

    public void destroy() {
        Native.rs2DeleteStreamProfilesList(streamProfileList);
    }

}