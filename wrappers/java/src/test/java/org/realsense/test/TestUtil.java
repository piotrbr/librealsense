package org.realsense.test;

import java.io.File;

public class TestUtil {
    public static void loadLibraries()
    {
        // load native os specific libraries
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // TODO: implement windows support for tests
        } else if (os.contains("mac")) {
            System.load(new File("src/main/resources/lib/org.librealsense/osx-x64/librealsense2.dylib").getAbsolutePath());
            System.load(new File("src/main/resources/lib/org.librealsense/osx-x64/libnative.dylib").getAbsolutePath());
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            // TODO: implement unix support for test
        } else {
            // Operating System not supported!
        }
    }

}
