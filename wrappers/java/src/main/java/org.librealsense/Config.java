package org.librealsense;

public class Config {

    protected long config;

    protected Config(long config) {
        this.config = config;
    }

    public static Config create() {
        return new Config(Native.rs2CreateConfig());
    }

    public void enableDevice(Device device) {
        Native.rs2ConfigEnableDevice(config, device.serial());
    }

    public void enableStream(Native.Stream stream, int index, int width, int height, Native.Format format, int fps) {
        Native.rs2ConfigEnableStream(config, stream.ordinal(), index, width, height, format.ordinal(), fps);
    }
}