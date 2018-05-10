package org.librealsense;

public class Projection {

    public static float[] deprojectPixelToPoint(Intrinsics instrinsics, float px, float py, float depth) {


        if (instrinsics.model != Native.RS2_BROWN_CONRADY) {
            throw new RuntimeException("unsupported mode");
        }

        float x = (px - instrinsics.ppx) / instrinsics.fx;
        float y = (py - instrinsics.ppy) / instrinsics.fy;


        return new float[] { depth * x, depth * y, depth };

    }

}