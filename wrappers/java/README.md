# Java wrapper for librealsense

Work in progress, currently targeting Windows 10. At this point it works well enough to receive depth streams from multiple sensors, but all other use is currently highly untested.

## Building

```sh
./gradlew build
./gradlew publishToMavenLocal
```
Builds with JDK 8, seems to fail with JDK 9.

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
val context = Context.create()
val deviceList = context.queryDevices()
val devices = deviceList.devices

val device = devices[0]
val pipeline = context.createPipeline()
val config = Config.create()
config.enableDevice(device)
config.enableStream(Stream.RS2_STREAM_DEPTH, 0, 640, 480, Format.RS2_FORMAT_Z16, 30)
pipeline.startWithConfig(config)

while (true) {
    val frames = pipeline.waitForFrames(5000)

    for (i in 0 until frames.frameCount) {
        val frame = frames.frame(i)
        val buffer = frame.frameData
        // -- use ByteBuffer here
        frame.release()
    }
    frames.release()
}
```
