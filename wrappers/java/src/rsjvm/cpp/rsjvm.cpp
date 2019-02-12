#include "org_librealsense_Native.h"
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
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

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteContext
  (JNIEnv *env, jclass, jlong contextAddr) {
    rs2_context* context = (rs2_context*) contextAddr;
    rs2_delete_context(context);
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

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteDevice
  (JNIEnv *env, jclass, jlong deviceAddr) {
    rs2_device* device = (rs2_device*) deviceAddr;
    rs2_delete_device(device);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2HardwareReset
  (JNIEnv *env, jclass, jlong deviceAddr) {
    rs2_error *error = NULL;
    rs2_device* device = (rs2_device*) deviceAddr;
    rs2_hardware_reset(device, &error);
    checkErrors(env, error);
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

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2PipelineStop
  (JNIEnv *env, jclass, jlong pipeAddr) {
    rs2_error *error = NULL;
    rs2_pipeline* pipe = (rs2_pipeline*)pipeAddr;
    rs2_pipeline_stop(pipe, &error);
    checkErrors(env, error);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeletePipeline
  (JNIEnv *env, jclass, jlong pipeAddr) {
    rs2_pipeline* pipe = (rs2_pipeline*)pipeAddr;
    rs2_delete_pipeline(pipe);
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

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PipelineProfileGetStreams
  (JNIEnv *env, jclass, jlong pipelineProfileAddr)  {
    rs2_pipeline_profile* pipelineProfile = (rs2_pipeline_profile*) pipelineProfileAddr;
    rs2_error *error = NULL;

    rs2_stream_profile_list*  list = rs2_pipeline_profile_get_streams(pipelineProfile, &error);
    checkErrors(env, error);
    return (jlong)list;

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

 JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2GetFrameStreamProfile
   (JNIEnv *env, jclass, jlong frameAddr) {
   rs2_error *error = NULL;
     rs2_frame* frame = (rs2_frame*) frameAddr;
     const rs2_stream_profile* streamProfile = rs2_get_frame_stream_profile(frame, &error);
     checkErrors(env, error);
     return (jlong)streamProfile;
 }

 JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2GetFrameNumber
    (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_error *error = NULL;
      rs2_frame* frame = (rs2_frame*) frameAddr;
      unsigned long long frameNumber = rs2_get_frame_number(frame, &error);
      checkErrors(env, error);
      return (jlong)frameNumber;
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

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFrameStrideInBytes
  (JNIEnv *, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*)frameAddr;
    return (jint)rs2_get_frame_stride_in_bytes(frame, NULL);
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
    int stride = rs2_get_frame_stride_in_bytes(frame, NULL);
    int capacity = stride * height;

    const void * data = rs2_get_frame_data(frame, &error);
    checkErrors(env, error);
    jobject directBuffer = env->NewDirectByteBuffer((void*)(data), capacity);
    return directBuffer;
}

JNIEXPORT jobject JNICALL Java_org_librealsense_Native_rs2GetFrameVertices
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_frame* frame = (rs2_frame*) frameAddr;
    rs2_error *error = NULL;

    int capacity = rs2_get_frame_points_count(frame, &error);

    const void * data = rs2_get_frame_vertices(frame, &error);
    checkErrors(env, error);
    jobject directBuffer = env->NewDirectByteBuffer((void*)(data), capacity);
    return directBuffer;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2ExportToPly
  (JNIEnv *env, jclass, jlong frameAddr, jstring fileName, jlong textureAddr) {
    rs2_error *error = NULL;
    const char *fname = env->GetStringUTFChars(fileName, NULL);
    rs2_frame* frame = (rs2_frame*)frameAddr;
    rs2_frame* texture = (rs2_frame*)textureAddr;
    rs2_export_to_ply(frame, fname, texture, &error);
    checkErrors(env, error);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2FrameAddRef
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_error *error = NULL;
    rs2_frame* frame = (rs2_frame*)frameAddr;
    rs2_frame_add_ref(frame, &error);
    checkErrors(env, error);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2ProcessFrame
  (JNIEnv *env, jclass, jlong blockAddr, jlong frameAddr) {
    rs2_error *error = NULL;
    rs2_processing_block* block = (rs2_processing_block*)blockAddr;
    rs2_frame* frame = (rs2_frame*)frameAddr;
    rs2_process_frame(block, frame, &error);
    checkErrors(env, error);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2PollForFrame
  (JNIEnv *env, jclass, jlong queueAddr) {
    rs2_error *error = NULL;
    rs2_frame_queue* queue = (rs2_frame_queue*)queueAddr;
    rs2_frame* frame;

    int result = rs2_poll_for_frame(queue, &frame, &error);
    checkErrors(env, error);

    return result == 1 ? (jlong)frame : -1;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateFrameQueue
  (JNIEnv *env, jclass, jint capacity) {
    rs2_error *error = NULL;
    rs2_frame_queue* queue = rs2_create_frame_queue(capacity, &error);
    checkErrors(env, error);

    return (jlong)queue;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateColorizer
  (JNIEnv *env, jclass) {
    rs2_error *error = NULL;
    rs2_processing_block* block = rs2_create_colorizer(&error);
    checkErrors(env, error);

    return (jlong)block;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateAlign
  (JNIEnv *env, jclass, jint stream) {
    rs2_error *error = NULL;
    rs2_processing_block* block = rs2_create_align(static_cast<rs2_stream>(stream), &error);
    checkErrors(env, error);

    return (jlong)block;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreatePointCloud
  (JNIEnv *env, jclass) {
    rs2_error *error = NULL;
    rs2_processing_block* block = rs2_create_pointcloud(&error);
    checkErrors(env, error);

    return (jlong)block;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetFramePointsCount
  (JNIEnv *env, jclass, jlong frameAddr) {
    rs2_error *error = NULL;
    rs2_frame* frame = (rs2_frame*)frameAddr;
    int count = rs2_get_frame_points_count(frame, &error);
    checkErrors(env, error);

    return count;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2StartProcessingQueue
  (JNIEnv *env, jclass, jlong blockAddr, jlong queueAddr) {
    rs2_error *error = NULL;
    rs2_processing_block* block = (rs2_processing_block*)blockAddr;
    rs2_frame_queue* queue = (rs2_frame_queue*)queueAddr;
    rs2_start_processing_queue(block, queue, &error);
    checkErrors(env, error);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2QuerySensors
  (JNIEnv *env, jclass, jlong deviceAddr) {
    rs2_error *error = NULL;
    rs2_device* device = (rs2_device*) deviceAddr;
    rs2_sensor_list* sensorList = rs2_query_sensors(device, &error);
    checkErrors(env, error);
    return (jlong)sensorList;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetSensorsCount
  (JNIEnv *env, jclass, jlong sensorListAddr) {
    rs2_error *error = NULL;
    rs2_sensor_list* sensorList = (rs2_sensor_list*)sensorListAddr;
    int count = rs2_get_sensors_count(sensorList, &error);
    checkErrors(env, error);
    return (jint)count;
 }

 JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteSensorList
   (JNIEnv *, jclass, jlong sensorListAddr) {
    rs2_sensor_list *sensorList = (rs2_sensor_list*) sensorListAddr;
    rs2_delete_sensor_list(sensorList);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteSensor
  (JNIEnv *, jclass, jlong sensorAddr) {
    rs2_sensor *sensor = (rs2_sensor*) sensorAddr;
    rs2_delete_sensor(sensor);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2CreateSensor
  (JNIEnv *env, jclass, jlong sensorListAddr, jint index) {
    rs2_error *error = NULL;
    rs2_sensor_list *sensorList = (rs2_sensor_list*) sensorListAddr;
    rs2_sensor* sensor = rs2_create_sensor(sensorList, index, &error);
    checkErrors(env, error);
    return (jlong)sensor;
}

JNIEXPORT jstring JNICALL Java_org_librealsense_Native_rs2GetSensorInfo
  (JNIEnv *env, jclass, jlong sensorAddr, jint cameraInfo) {
    rs2_error *error = NULL;
    rs2_sensor* sensor = (rs2_sensor*) sensorAddr;

    const char *info = rs2_get_sensor_info(sensor, static_cast<rs2_camera_info>(cameraInfo), &error);
    jstring jInfo = env->NewStringUTF(info);
    return jInfo;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2SupportsSensorInfo
  (JNIEnv *env, jclass, jlong sensorAddr, jint cameraInfo) {
    rs2_error *error = NULL;
    rs2_sensor* sensor = (rs2_sensor*) sensorAddr;

    int supported = rs2_supports_sensor_info(sensor, static_cast<rs2_camera_info>(cameraInfo), &error);
    checkErrors(env, error);

    return (jint) supported;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2IsSensorExtendableTo
  (JNIEnv *env, jclass, jlong sensorAddr, jint extension) {
    rs2_error *error = NULL;
    rs2_sensor* sensor = (rs2_sensor*) sensorAddr;
    int extendable = rs2_is_sensor_extendable_to(sensor, static_cast<rs2_extension>(extension), &error);
    checkErrors(env, error);
    return (jint)extendable;
}

JNIEXPORT jfloat JNICALL Java_org_librealsense_Native_rs2GetDepthScale
  (JNIEnv *env, jclass, jlong sensorAddr) {
    rs2_sensor* sensor = (rs2_sensor*) sensorAddr;
    rs2_error *error = NULL;
    float depth = rs2_get_depth_scale(sensor, &error);
    checkErrors(env, error);
    return (jfloat)depth;
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2GetStreamProfiles
  (JNIEnv *env, jclass, jlong sensorAddr) {
    rs2_error *error = NULL;
    rs2_sensor* sensor = (rs2_sensor*) sensorAddr;
    rs2_stream_profile_list* streamProfileList = rs2_get_stream_profiles(sensor, &error);
    checkErrors(env, error);
    return (jlong)streamProfileList;
}

JNIEXPORT jint JNICALL Java_org_librealsense_Native_rs2GetStreamProfileCount
  (JNIEnv *env, jclass, jlong streamProfileListAddr) {
    rs2_error *error = NULL;

    rs2_stream_profile_list* streamProfileList = (rs2_stream_profile_list*) streamProfileListAddr;
    int count = rs2_get_stream_profiles_count(streamProfileList, &error);
    checkErrors(env, error);
    return (jint)count;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteStreamProfilesList
  (JNIEnv *, jclass, jlong streamProfileListAddr) {
    rs2_stream_profile_list* streamProfileList = (rs2_stream_profile_list*) streamProfileListAddr;
    rs2_delete_stream_profiles_list(streamProfileList);
}

JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2GetStreamProfile
  (JNIEnv *env, jclass, jlong streamProfileListAddr, jint index) {
    rs2_error *error = NULL;

    rs2_stream_profile_list* streamProfileList = (rs2_stream_profile_list*) streamProfileListAddr;
    const rs2_stream_profile* streamProfile = rs2_get_stream_profile(streamProfileList, index, &error);

    checkErrors(env, error);
    return (jlong) streamProfile;
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2DeleteStreamProfile
  (JNIEnv *, jclass, jlong streamProfileAddr) {
  rs2_stream_profile* streamProfile = (rs2_stream_profile*) streamProfileAddr;
  rs2_delete_stream_profile(streamProfile);
}

JNIEXPORT void JNICALL Java_org_librealsense_Native_rs2GetStreamProfileData
  (JNIEnv *env, jclass, jlong streamProfileAddr, jobject obj) {
    rs2_error *error = NULL;
    rs2_stream_profile* streamProfile = (rs2_stream_profile*) streamProfileAddr;

    // data fields
    rs2_stream stream;
    rs2_format format;
    int index;
    int unique_id;
    int framerate;

    rs2_get_stream_profile_data(streamProfile, &stream, &format, &index, &unique_id, &framerate, &error);
    checkErrors(env, error);

    jclass jData = env->FindClass("org/librealsense/streamprofiles/StreamProfileData");

    jfieldID jStreamIndex = env->GetFieldID(jData, "nativeStreamIndex", "I");
    env->SetIntField(obj, jStreamIndex, (jint)stream);

    jfieldID jFormatIndex = env->GetFieldID(jData, "nativeFormatIndex", "I");
    env->SetIntField(obj, jFormatIndex, (jint)format);

    jfieldID jIndex = env->GetFieldID(jData, "index", "I");
    env->SetIntField(obj, jIndex, index);

    jfieldID jUniqueId = env->GetFieldID(jData, "uniqueId", "I");
    env->SetIntField(obj, jUniqueId, unique_id);

    jfieldID jFrameRate = env->GetFieldID(jData, "frameRate", "I");
    env->SetIntField(obj, jFrameRate, framerate);
}


JNIEXPORT jlong JNICALL Java_org_librealsense_Native_rs2GetVideoStreamIntrinsics
  (JNIEnv *env, jclass, jlong streamProfileAddr, jobject obj) {
    rs2_error *error = NULL;
    rs2_stream_profile* streamProfile = (rs2_stream_profile*) streamProfileAddr;
    rs2_intrinsics* intrinsics = (rs2_intrinsics*)malloc(sizeof(rs2_intrinsics));
    rs2_get_video_stream_intrinsics(streamProfile, intrinsics, &error);


    jclass jIntrinsics = env->FindClass("org/librealsense/types/Intrinsics");

    jfieldID jWidth = env->GetFieldID(jIntrinsics, "width", "I");
    env->SetIntField(obj, jWidth, intrinsics->width);

    jfieldID jHeight = env->GetFieldID(jIntrinsics, "height", "I");
    env->SetIntField(obj, jHeight, intrinsics->height);

     jfieldID jPpx = env->GetFieldID(jIntrinsics, "ppx", "F");
     env->SetFloatField(obj, jPpx, intrinsics->ppx);

     jfieldID jPpy = env->GetFieldID(jIntrinsics, "ppy", "F");
     env->SetFloatField(obj, jPpy, intrinsics->ppy);

     jfieldID jFx = env->GetFieldID(jIntrinsics, "fx", "F");
     env->SetFloatField(obj, jFx, intrinsics->fx);

     jfieldID jFy = env->GetFieldID(jIntrinsics, "fy", "F");
     env->SetFloatField(obj, jFy, intrinsics->fy);

     jfieldID jModel = env->GetFieldID(jIntrinsics, "model", "I");
     env->SetIntField(obj, jModel, static_cast<int> (intrinsics->model));

     jfieldID jCoeffs = env->GetFieldID(jIntrinsics, "coeffs", "[F");
     jfloatArray jCoeffsArray = (jfloatArray) env->GetObjectField(obj, jCoeffs);
     jfloat* jCoeffsBody = env->GetFloatArrayElements(jCoeffsArray, 0);


        printf("%f %f %f %f %f\n", intrinsics->coeffs[0],intrinsics->coeffs[1],
        intrinsics->coeffs[2],intrinsics->coeffs[3],intrinsics->coeffs[4]);
     jCoeffsBody[0] = intrinsics->coeffs[0];
     jCoeffsBody[1] = intrinsics->coeffs[1];
     jCoeffsBody[2] = intrinsics->coeffs[2];
     jCoeffsBody[3] = intrinsics->coeffs[3];
     jCoeffsBody[4] = intrinsics->coeffs[4];

     env->ReleaseFloatArrayElements(jCoeffsArray, jCoeffsBody, 0);

    return (jlong)intrinsics;
}
