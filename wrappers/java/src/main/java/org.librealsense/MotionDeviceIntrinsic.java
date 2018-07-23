package org.librealsense;

class MotionDeviceIntrinsic {

    long motionDeviceIntrinsic;
    protected MotionDeviceIntrinsic(long motionDeviceIntrinsic) {
        this.motionDeviceIntrinsic = motionDeviceIntrinsic;
    }

    public long getPtr() {
        return motionDeviceIntrinsic;
    }
}