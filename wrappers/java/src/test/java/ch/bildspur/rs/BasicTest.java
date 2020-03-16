package ch.bildspur.rs;

import org.junit.Ignore;
import org.junit.Test;
import org.librealsense.Native;

@Ignore
public class BasicTest {

    @Test
    public void testRealSenseCameraAvailable()
    {
        Native.loadNativeLibraries("libs");
    }
}
