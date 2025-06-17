package com.lurch.display.geom;

/**
 * Represents a quad in OpenGL.
 */
public class Quad
{
    /**
     * The mesh representing the quad.
     * This contains the vertex and index data for rendering.
     */
    private final Mesh mesh;


    /**
     * Vertices for a quad centered around the origin.
     * Positions coordnates range from -0.5 to 0.5.
     * Texture coordnates range from 0 to 1.
     * Each vertex has 4 floats: x, y (position), u, v (texture coordinate).
     */
    private static final float[] VERTICES = 
    {
        // Pos         // Tex
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
     * Constructs a Quad instance.
     * Initializes the mesh with the defined vertices and indices.
     */
    public Quad()
    {
        /* 4 floats per vertex: x, y, u, v */
        mesh = new Mesh(VERTICES, INDICES, 4);
    }


    /**
     * Renders the quad using the associated mesh.
     * This method should be called within the rendering loop to draw the quad.
     */
    public void render() {

        mesh.render();
    }


    /**
     * Deletes the mesh resources.
     * This should be called when the quad is no longer needed to free up OpenGL resources.
     */
    public void delete() 
    {
        mesh.delete();
    }
}
