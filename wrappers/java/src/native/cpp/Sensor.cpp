#include "org_librealsense_Sensor.h"
#include <stdio.h>
#include <stdint.h>
#include <librealsense2/rs.h>

JNIEXPORT jlong JNICALL Java_org_librealsense_Sensor_rs2CreateContext
  (JNIEnv *env, jobject nativeMethods, jint apiVersion) {

    rs2_context* context = rs2_create_context(RS2_API_VERSION, NULL);

    return (jlong) 0;
}

