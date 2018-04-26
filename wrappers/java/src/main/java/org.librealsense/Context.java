package org.librealsense;

public class Context {

    private long context;
    private Context(long context) {
        this.context = context;
    }

    public static Context create() {
        long context = Native.rs2CreateContext(0);
        return new Context(context);
    }

    public Pipeline createPipeline() {
        long pipeline = Native.rs2CreatePipeline(context);
        return new Pipeline(pipeline);
    }

    public DeviceList queryDevices() {
        return new DeviceList(Native.rs2QueryDevices(context));
    }
}