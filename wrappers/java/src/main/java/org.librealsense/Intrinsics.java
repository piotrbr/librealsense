package org.librealsense;

public class Intrinsics {
    public int width;
    /**
     * < Width of the image in pixels
     */
    public int height;
    /**
     * < Height of the image in pixels
     */
    public float ppx;
    /**
     * < Horizontal coordinate of the principal point of the image, as a pixel offset from the left edge
     */
    public float ppy;
    /**
     * < Vertical coordinate of the principal point of the image, as a pixel offset from the top edge
     */
    public float fx;
    /**
     * < Focal length of the image plane, as a multiple of pixel width
     */
    public float fy;
    /**
     * < Focal length of the image plane, as a multiple of pixel height
     */
    public int model;
    /**
     * < Distortion model of the image
     */
    public float[] coeffs = new float[5]; /**< Distortion coefficients, order: k1, k2, p1, p2, k3 */
}