package com.lurch.display.mesh;

/**
 * Represents a quad in OpenGL.
 * This quads coordnates are tightly bound to texture coordinates,
 * making it suitable for rendering textures with UV mapping.
 */
public class UVQuad extends Mesh
{
    /**
     * Vertices for a UV-aligned quad.
     * Coordinates range from 0 to 1 in both axes.
     * Each vertex has 4 floats: x, y (position), u, v (texture coordinate).
     */
    private static final float[] VERTICES = 
    {
        // Pos       // Tex
        0f, 0f,      0f, 0f,
        1f, 0f,      1f, 0f,
        1f, 1f,      1f, 1f,
        0f, 1f,      0f, 1f
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
     * Constructs a Quad instance.
     * Initializes the mesh with the defined vertices and indices.
     */
    public UVQuad()
    {
        /* 4 floats per vertex: x, y, u, v */
        super(VERTICES, INDICES, 4);
    }
}
