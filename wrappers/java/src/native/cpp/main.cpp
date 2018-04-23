#include "org_librealsense_Native.h"
#include <stdio.h>
#include <stdint.h>
#include <librealsense2/rs.h>
#include <librealsense2/h/rs_pipeline.h>
#include <librealsense2/h/rs_frame.h>

JNIEXPORT void JNICALL Java_org_librealsense_Native_helloThere
  (JNIEnv *, jclass) {
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateContext
  (JNIEnv *, jclass, jint) {
    rs2_context* context = rs2_create_context(RS2_API_VERSION, NULL);
    return (jlong) context;
}


JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2QueryDevices
  (JNIEnv *, jclass, jlong contextAddr) {

    rs2_context* context = (rs2_context*) contextAddr;
    rs2_device_list* device_list = rs2_query_devices(context, NULL);
    return (jlong)device_list;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetDeviceCount
  (JNIEnv *, jclass, jlong deviceListAddr) {

    rs2_device_list* device_list = (rs2_device_list*) deviceListAddr;
    int count = rs2_get_device_count(device_list, NULL);
    return (jint) count;
}


JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateDevice
  (JNIEnv *, jclass, jlong deviceListAddr, jint deviceIndex) {

    rs2_device_list* device_list = (rs2_device_list*) deviceListAddr;
    rs2_device* device = rs2_create_device(device_list, deviceIndex, NULL);
    return (jlong) device;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2SupportsDeviceInfo
  (JNIEnv *, jclass, jlong deviceAddr, jint cameraInfo) {

    rs2_device* device = (rs2_device*) deviceAddr;
    int supported = rs2_supports_device_info(device, static_cast<rs2_camera_info>(cameraInfo), NULL);
    return (jint) supported;
}

JNIEXPORT jstring JNICALL Java_org_librealsense_Native_rs2GetDeviceInfo
  (JNIEnv *env, jclass, jlong deviceAddr, jint cameraInfo) {
    rs2_device* device = (rs2_device*) deviceAddr;
    const char * info = rs2_get_device_info(device, static_cast<rs2_camera_info>(cameraInfo), NULL);
    jstring jInfo = env->NewStringUTF(info);
    return jInfo;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreatePipeline
  (JNIEnv *, jclass, jlong contextAddr) {
    rs2_context* context = (rs2_context*) contextAddr;
    rs2_pipeline* pipeline = rs2_create_pipeline(context, NULL);
    return (jlong) pipeline;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateConfig
  (JNIEnv *, jclass) {
    rs2_config* config = rs2_create_config(NULL);
    return (jlong) config;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2ConfigEnableStream
  (JNIEnv *, jclass, jlong configAddr, jint stream, jint index, jint width, jint height, jint format, jint framerate) {
    rs2_config* config = (rs2_config*) configAddr;
	rs2_config_enable_stream(config, static_cast<rs2_stream>(stream), index, width, height, static_cast<rs2_format>(format), framerate, NULL);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PipelineStartWithConfig
  (JNIEnv *, jclass, jlong pipelineAddr, jlong configAddr) {

    rs2_pipeline* pipeline = (rs2_pipeline*) pipelineAddr;
    rs2_config *config = (rs2_config*) configAddr;

    rs2_error *e = 0;
    rs2_pipeline_profile* pipeline_profile = rs2_pipeline_start_with_config(pipeline, config, &e);

    if (e) {
        printf("error while starting pipeline!\n");
    }

    return (jlong)pipeline_profile;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PipelineWaitForFrames
  (JNIEnv *, jclass, jlong pipelineAddr, jint timeOut) {
    rs2_pipeline* pipeline = (rs2_pipeline*) pipelineAddr;
    rs2_frame* frame = rs2_pipeline_wait_for_frames(pipeline, timeOut, NULL);
    return (jlong)frame;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2EmbeddedFramesCount
  (JNIEnv *, jclass, jlong framesAddr) {
  rs2_frame* frames = (rs2_frame*) framesAddr;
  int frame_count = rs2_embedded_frames_count(frames, NULL);
  return (jint)frame_count;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2ExtractFrame
  (JNIEnv *, jclass, jlong framesAddr, jint index) {
    rs2_frame* frames = (rs2_frame*) framesAddr;
    rs2_frame* frame = rs2_extract_frame(frames, index, NULL);
    return (jlong)frame;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2ReleaseFrame
  (JNIEnv *, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*) frameAddr;
    rs2_release_frame(frame);
    return (jlong)0;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2IsFrameExtendableTo
  (JNIEnv *, jclass, jlong frameAddr, jint extensionOrd) {
  rs2_frame* frame = (rs2_frame*)frameAddr;
  rs2_extension extension = static_cast<rs2_extension>(extensionOrd);
  return (jint)rs2_is_frame_extendable_to(frame, extension, NULL);
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFrameWidth
  (JNIEnv *, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    return (jint)rs2_get_frame_width(frame, NULL);
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFrameHeight
  (JNIEnv *, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    return (jint)rs2_get_frame_height(frame, NULL);
}

JNIEXPORT jfloat JNICALL Java_org_librealsense_Native_rs2DepthFrameGetDistance
  (JNIEnv *, jclass, jlong frameAddr, jint x, jint y) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    float depth = rs2_depth_frame_get_distance(frame, x, y, NULL);
    return (jfloat)depth;
}
