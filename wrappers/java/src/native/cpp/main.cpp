#include "org_librealsense_Native.h"
#include <stdio.h>
#include <stdint.h>
#include <librealsense2/rs.h>

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
    rs2_supports_device_info(device, static_cast<rs2_camera_info>(cameraInfo), NULL);
}

JNIEXPORT jstring JNICALL Java_org_librealsense_Native_rs2GetDeviceInfo
  (JNIEnv *env, jclass, jlong deviceAddr, jint cameraInfo) {
    rs2_device* device = (rs2_device*) deviceAddr;
    const char * info = rs2_get_device_info(device, static_cast<rs2_camera_info>(cameraInfo), NULL);
    jstring jInfo = env->NewStringUTF(info);
    return jInfo;
}
