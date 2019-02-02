# Java wrapper for librealsense

Work in progress, currently targeting Windows 10 and Mac OSX. At this point it works well enough to receive depth streams from multiple sensors, but all other use is currently highly untested.

Supported Version: `RealSense 2.17.0`

## Building

First you have to build librealsense itself on your platform. Then use [gradle 4.9 or lower](https://github.com/cansik/realsense-processing/issues/2#issuecomment-450195961) to build the native wrapper and the java binary.

```sh
# windows
./gradlew build

# osx / unix
gradle build
```
Builds with JDK 8, seems to fail with JDK 9.

Under OSX, the native libraries of realsense are not bundled with the jar. You have to copy all the library files (and soft links) into the following directory:

```
/librealsense/wrappers/java/src/main/resources/lib/org.librealsense/osx-x64
```

## Using pre-built binaries

Currently Windows only! Ready to go jar files are hosted on bintray.

```groovy
repositories {
    maven {
        url = "https://dl.bintray.com/edwinrndr/librealsense"
    }
}

dependencies {
    compile "org.librealsense:realsense-jvm:2.2.10.4-0"
}
```

## Example use

The following shows to use the bindings from a Kotlin program. (Works from Java perfectly fine too.)

```kotlin
// load native libraries
Native.loadNativeLibraries()

val instance = Context.create()
val instance = instance.queryDevices()
val devices = instance.devices

val instance = devices[0]
val instance = instance.createPipeline()
val instance = Config.create()
instance.enableDevice(instance)
instance.enableStream(Stream.RS2_STREAM_DEPTH, 0, 640, 480, Format.RS2_FORMAT_Z16, 30)
instance.startWithConfig(instance)

while (true) {
    val frames = instance.waitForFrames(5000)

    for (i in 0 until frames.frameCount) {
        val instance = frames.instance(i)
        val buffer = instance.frameData
        // -- use ByteBuffer here
        instance.release()
    }
    frames.release()
}
```
