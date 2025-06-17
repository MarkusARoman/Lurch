package com.lurch.display.geom;

/**
 * Represents a quad in OpenGL.
 * Extends Mesh to directly act as a mesh with quad vertex and index data.
 */
public class Quad extends Mesh
{
    /**
     * Vertices for a quad centered around the origin.
     * Position coordinates range from -0.5 to 0.5.
     * Texture coordinates range from 0 to 1.
     * Each vertex has 4 floats: 
     *     x, y (position), 
     *     u, v (texture).
     */
    private static final float[] VERTICES = 
    {
        // Pos             // Tex
        -0.5f, -0.5f,      0f, 0f,
         0.5f, -0.5f,      1f, 0f,
         0.5f,  0.5f,      1f, 1f,
        -0.5f,  0.5f,      0f, 1f
    };
    

    /**
     * Indices for the quad vertices.
     * The quad is made up of two triangles.
     */
    private static final int[] INDICES = 
    {
        0, 1, 2,
        0, 2, 3
    };
    

    /**
     * Constructs a Quad instance by calling super with vertex and index data.
     */
    public Quad()
    {
        // 4 floats per vertex: x, y, u, v
        super(VERTICES, INDICES, 4);
    }
}
