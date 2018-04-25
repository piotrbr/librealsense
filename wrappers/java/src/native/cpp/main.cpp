#include "org_librealsense_Native.h"
#include <stdio.h>
#include <stdint.h>
#include <librealsense2/rs.h>
#include <librealsense2/h/rs_pipeline.h>
#include <librealsense2/h/rs_frame.h>

void checkErrors(JNIEnv *env, rs2_error *error) {
    if (error != NULL) {
        char *className = "java/lang/RuntimeException";
        jclass reClass = env->FindClass(className);
        env->ThrowNew(reClass, rs2_get_error_message(error));
    }
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_helloThere
  (JNIEnv *env, jclass) {
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateContext
  (JNIEnv *env, jclass, jint) {
    rs2_error *error = NULL;
    rs2_context* context = rs2_create_context(RS2_API_VERSION, &error);
    checkErrors(env, error);
    return (jlong) context;
}


JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2QueryDevices
  (JNIEnv *env, jclass, jlong contextAddr) {
    rs2_error *error = NULL;
    rs2_context* context = (rs2_context*) contextAddr;
    rs2_device_list* device_list = rs2_query_devices(context, &error);
    checkErrors(env, error);
    return (jlong)device_list;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetDeviceCount
  (JNIEnv *env, jclass, jlong deviceListAddr) {
    rs2_error *error = NULL;
    rs2_device_list* device_list = (rs2_device_list*) deviceListAddr;
    int count = rs2_get_device_count(device_list, &error);
    checkErrors(env, error);
    return (jint) count;
}


JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateDevice
  (JNIEnv *env, jclass, jlong deviceListAddr, jint deviceIndex) {
    rs2_error *error = NULL;
    rs2_device_list* device_list = (rs2_device_list*) deviceListAddr;
    rs2_device* device = rs2_create_device(device_list, deviceIndex, &error);
    checkErrors(env, error);
    return (jlong) device;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2SupportsDeviceInfo
  (JNIEnv *env, jclass, jlong deviceAddr, jint cameraInfo) {
    rs2_error *error = NULL;
    rs2_device* device = (rs2_device*) deviceAddr;
    int supported = rs2_supports_device_info(device, static_cast<rs2_camera_info>(cameraInfo), &error);
    checkErrors(env, error);
    return (jint) supported;
}

JNIEXPORT jstring JNICALL Java_org_librealsense_Native_rs2GetDeviceInfo
  (JNIEnv *env, jclass, jlong deviceAddr, jint cameraInfo) {
    rs2_error *error = NULL;
    rs2_device* device = (rs2_device*) deviceAddr;
    const char * info = rs2_get_device_info(device, static_cast<rs2_camera_info>(cameraInfo), &error);
    jstring jInfo = env->NewStringUTF(info);
    checkErrors(env, error);
    return jInfo;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreatePipeline
  (JNIEnv *env, jclass, jlong contextAddr) {
    rs2_error *error = NULL;
    rs2_context* context = (rs2_context*) contextAddr;
    rs2_pipeline* pipeline = rs2_create_pipeline(context, &error);
    checkErrors(env, error);
    return (jlong) pipeline;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateConfig
  (JNIEnv *env, jclass) {
    rs2_error *error = NULL;
    rs2_config* config = rs2_create_config(&error);
    checkErrors(env, error);
    return (jlong) config;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteConfig
  (JNIEnv *, jclass, jlong configAddr) {
    rs2_config* config = (rs2_config*) configAddr;
    rs2_delete_config(config);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2ConfigEnableStream
  (JNIEnv *env, jclass, jlong configAddr, jint stream, jint index, jint width, jint height, jint format, jint framerate) {
    rs2_error *error = NULL;
    rs2_config* config = (rs2_config*) configAddr;
	rs2_config_enable_stream(config, static_cast<rs2_stream>(stream), index, width, height, static_cast<rs2_format>(format), framerate, &error);
	checkErrors(env, error);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2ConfigEnableDevice
  (JNIEnv *env, jclass, jlong configAddr, jstring jSerial) {
    rs2_error *error = NULL;
    rs2_config* config = (rs2_config*) configAddr;
    const char *serial = env->GetStringUTFChars(jSerial, 0);
    rs2_config_enable_device(config, serial, &error);
    env->ReleaseStringUTFChars(jSerial, serial);
    checkErrors(env, error);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PipelineStartWithConfig
  (JNIEnv *env, jclass, jlong pipelineAddr, jlong configAddr) {
    rs2_error *error = NULL;
    rs2_pipeline* pipeline = (rs2_pipeline*) pipelineAddr;
    rs2_config *config = (rs2_config*) configAddr;
    rs2_pipeline_profile* pipeline_profile = rs2_pipeline_start_with_config(pipeline, config, &error);
    checkErrors(env, error);
    return (jlong)pipeline_profile;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PipelineWaitForFrames
  (JNIEnv *env, jclass, jlong pipelineAddr, jint timeOut) {
    rs2_error *error = NULL;
    rs2_pipeline* pipeline = (rs2_pipeline*) pipelineAddr;
    rs2_frame* frame = rs2_pipeline_wait_for_frames(pipeline, timeOut, &error);
    checkErrors(env, error);
    return (jlong)frame;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2EmbeddedFramesCount
  (JNIEnv *env, jclass, jlong framesAddr) {
    rs2_error *error = NULL;
    rs2_frame* frames = (rs2_frame*) framesAddr;
    int frame_count = rs2_embedded_frames_count(frames, &error);
    checkErrors(env, error);
    return (jint)frame_count;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2ExtractFrame
  (JNIEnv *env, jclass, jlong framesAddr, jint index) {
    rs2_error *error = NULL;
    rs2_frame* frames = (rs2_frame*) framesAddr;
    rs2_frame* frame = rs2_extract_frame(frames, index, &error);
    checkErrors(env, error);

    return (jlong)frame;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2ReleaseFrame
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*) frameAddr;
    rs2_release_frame(frame);
    return (jlong)0;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2IsFrameExtendableTo
  (JNIEnv *env, jclass, jlong frameAddr, jint extensionOrd) {
    rs2_error *error = NULL;
    rs2_frame* frame = (rs2_frame*)frameAddr;
    rs2_extension extension = static_cast<rs2_extension>(extensionOrd);
    jint result = (jint)rs2_is_frame_extendable_to(frame, extension, &error);
    checkErrors(env, error);
    return result;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFrameWidth
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_error *error = NULL;
    rs2_frame* frame = (rs2_frame*)frameAddr;
    jint result = (jint)rs2_get_frame_width(frame, &error);
    checkErrors(env, error);
    return result;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFrameHeight
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    return (jint)rs2_get_frame_height(frame, NULL);
}

JNIEXPORT jfloat JNICALL Java_org_librealsense_Native_rs2DepthFrameGetDistance
  (JNIEnv *env, jclass, jlong frameAddr, jint x, jint y) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    float depth = rs2_depth_frame_get_distance(frame, x, y, NULL);
    return (jfloat)depth;
}

JNIEXPORT jobject JNICALL Java_org_librealsense_Native_rs2GetFrameData
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*) frameAddr;
    rs2_error *error = NULL;

    int width = rs2_get_frame_width(frame, NULL);
    int height = rs2_get_frame_height(frame, NULL);

    // TODO: calculate the correct capacity according to the data type.
    int capacity = width * height * 2;

    const void * data = rs2_get_frame_data(frame, &error);
    checkErrors(env, error);
    jobject directBuffer = env->NewDirectByteBuffer((void*)(data), capacity);
    return directBuffer;
}
