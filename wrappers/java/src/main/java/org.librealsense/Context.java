package org.librealsense;

public class Context {

    private long instance;

    private Context(long instance) {
        this.instance = instance;
    }

    public static Context create() {
        long context = Native.rs2CreateContext(0);
        return new Context(context);
    }

    public Pipeline createPipeline() {
        long pipeline = Native.rs2CreatePipeline(instance);
        return new Pipeline(pipeline);
    }

    public DeviceList queryDevices() {
        return new DeviceList(Native.rs2QueryDevices(instance));
    }
}