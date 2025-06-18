package com.lurch.display.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh 
{
    /**
     * Handle for the vertex array object.
     */
    private final int vao;


    /**
     * Handle for the vertex buffer object.
     */
    private final int vbo;


    /**
     * Handle for the element buffer object.
     */
    private final int ebo;


    /**
     * The number of indices in the mesh.
     */
    private final int indexCount;


    /**
     * Creates a mesh with the specified vertices and indices.
     *
     * @param vertices the vertex data (position and texture coordinates)
     * @param indices  the index data for drawing the mesh
     * @param stride   the stride in floats between consecutive vertex attributes
     */
    public Mesh(float[] vertices, int[] indices, int stride) 
    {
        /* Validate input */
        if (vertices == null || indices == null || stride <= 0 || 
            vertices.length < stride * 2 || indices.length == 0 || 
            vertices.length % stride != 0)
        {
            throw new IllegalArgumentException("Invalid mesh parameters");
        }


        /* Set index count */
        this.indexCount = indices.length;


        /* Create vertex array object */
        vao = glGenVertexArrays();
        glBindVertexArray(vao);


        /* Create vertex buffer object */
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);


        /* Create index buffer object */
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


        /*  Position attribute (2 floats: x, y) */
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        
        /* TexCoord attribute (2 floats: u, v) */
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);


        /* Unbind the vertex array object */
        glBindVertexArray(0);


        /* Unbind buffers */
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }


    /**
     * Renders the mesh using the currently bound shader program.
     */
    public void render() 
    {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
    

    /**
     * Deletes the mesh resources.
     */
    public void delete() 
    {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }
}
