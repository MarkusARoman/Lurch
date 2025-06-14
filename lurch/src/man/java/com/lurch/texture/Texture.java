package com.lurch.texture;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;


/**
 * Texture class for handling OpenGL textures.
 * This class encapsulates the texture ID, width, and height.
 * It provides methods to bind, unbind, and clean up the texture.
 */
public class Texture 
{
    // Texture ID
    // This ID is used to bind the texture in OpenGL
    private final int id;

    // Texture width and height
    // These values are used for texture mapping and rendering
    private final int width;
    private final int height;


    // -- Constructors -- //


    /**
     * Constructor for the Texture class.
     * 
     * @param id The OpenGL texture ID.
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public Texture(int id, int width, int height) 
    {
        this.id = id;
        this.width = width;
        this.height = height;
    }


    // -- Methods -- //


    /**
     * Bind the texture to a specific texture unit.
     * 
     * @param unit The texture unit to bind the texture to.
     */
    public void bind(int unit) 
    {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Unbind the texture from a specific texture unit.
     * 
     * @param unit The texture unit to unbind the texture from.
     */
    public void unbind(int unit) 
    {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Clean up the texture by deleting it from OpenGL.
     * This should be called when the texture is no longer needed.
     */
    public void delete() {

        glDeleteTextures(id);
    }

    /**
     * Bind the texture to the default texture unit (GL_TEXTURE0).
     * This is a convenience method for binding the texture without specifying a unit.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

    /**
     * Unbind the texture from the default texture unit (GL_TEXTURE0).
     * This is a convenience method for unbinding the texture without specifying a unit.
     */
    public void unbind() 
    {
        glBindTexture(GL_TEXTURE_2D, 0);   
    }


    // --- Getters --- //


    /**
     * Get the OpenGL texture ID.
     * 
     * @return The OpenGL texture ID.
     */
    public int getId() 
    {
        return id;
    }

    /**
     * Get the width of the texture.
     * 
     * @return The width of the texture.
     */
    public int getWidth() 
    {
        return width;
    }

    /**
     * Get the height of the texture.
     * 
     * @return The height of the texture.
     */
    public int getHeight() 
    {
        return height;
    }
}
