package org.librealsense;

import java.nio.ByteBuffer;

public class Points extends Frame {
    public Points(long instance) {
        super(instance);
    }

    public int getCount()
    {
        return Native.rs2GetFramePointsCount(instance);
    }

    public ByteBuffer rawVertexBuffer()
    {
        return Native.rs2GetFrameVertices(instance);
    }

    public void exportToPly(String fileName, Frame texture)
    {
        Native.rs2ExportToPly(instance, fileName, texture.instance);
    }
}
