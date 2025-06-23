package com.lurch.display.texture;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;


/**
 * Represents a texture in OpenGL.
 * This class encapsulates the creation, binding, and management of OpenGL textures.
 */
public class Texture 
{
    /**
     * Identifier for texture handle.
     */
    private final int handle;


    /**
     * Texture width in pixels.
     */
    private final int width;


    /**
     * Texture height in pixels.
     */
    private final int height;

    /**
     * Texture target type.
     * This is typically GL_TEXTURE_2D for 2D textures.
     */
    private final int target;

    /**
     * Internal format of the texture.
     * This defines how the texture data is stored in memory.
     */
    private final int internalFormat;

    /**
     * Format of the texture data.
     * This defines how the pixel data is interpreted.
     */
    private final int format;


    /**
     * Creates a texture with the specified width, height, target, internal format, and format.
     *
     * @param width          the width of the texture in pixels
     * @param height         the height of the texture in pixels
     * @param target         the target type of the texture (e.g., GL_TEXTURE_2D)
     * @param internalFormat the internal format of the texture (e.g., GL_RGBA8)
     * @param format         the format of the texture data (e.g., GL_RGBA)
     */
    public Texture(int width, int height, int target, int internalFormat, int format) 
    {
        /* Validate input */
        if (width <= 0 || height <= 0) 
        {
            throw new IllegalArgumentException("Invalid texture dimensions");
        }


        /* Set texture parameters */
        this.width = width;
        this.height = height;
        this.target = target;
        this.internalFormat = internalFormat;
        this.format = format;


        /* Generate texture handle */
        handle = glGenTextures();
    }


    /** 
     * Uploads image data.
     * 
     * @param data ByteBuffer containing the image data
     */
    public void upload(ByteBuffer data)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }


    /** 
     * Binds the texture to the specified texture unit 
     * @param unit the texture unit to bind to (e.g., 0 for GL_TEXTURE0)
     */
    public void bind() {
        glBindTexture(target, handle);
    }


    /** 
     * Binds the texture to the active texture unit 
     */
    public void unbind() {
        glBindTexture(target, 0);
    }



    /**
     * Release OpenGL resources associated with this texture.
     */
    public void delete() 
    {
        glDeleteTextures(handle);
    }


    /**
     * Gets the texture width.
     *
     * @return Texture width in pixels
     */
    public int getWidth() 
    {
        return width;
    }


    /**
     * Gets the texture height.
     *
     * @return Texture height in pixels
     */
    public int getHeight() 
    {
        return height;
    }


    /**
     * Gets the texture handle.
     *
     * @return OpenGL texture handle
     */
    public int getHandle()
    {
        return handle;
    }
}
