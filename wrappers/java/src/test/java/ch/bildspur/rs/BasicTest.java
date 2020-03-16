package ch.bildspur.rs;

import org.junit.Test;
import org.librealsense.Native;

public class BasicTest {

    //@Test
    public void testRealSenseCameraAvailable()
    {
        Native.loadNativeLibraries("libs");
    }
}
