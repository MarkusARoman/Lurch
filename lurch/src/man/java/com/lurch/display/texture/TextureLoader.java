package com.lurch.display.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import java.io.IOException;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_R8;

/**
 * Utility class for loading image files into OpenGL textures using STBImage.
 */
public class TextureLoader 
{
    /**
     * Base folder for texture assets.
     * Adjust this path to match your resource layout.
     */
    private static final String TEXTURE_FOLDER = "lurch/src/main/resource/texture/";


    /**
     * Loads a texture with default parameters: GL_TEXTURE_2D, GL_RGBA8, and GL_RGBA.
     *
     * @param name File name of the texture
     * 
     * @return Loaded {@link Texture} object
     * 
     * @throws IOException If loading fails or the file is not found
     */
    public static Texture load(String name) throws IOException
    {
        return load(name, GL_TEXTURE_2D, GL_RGBA8, GL_RGBA);
    }


    /**
     * Loads an image from file and creates an OpenGL texture object.
     *
     * @param name            File name of the texture
     * @param target          Texture target 
     * @param internalFormat  OpenGL internal format
     * @param format          Format of pixel data
     * 
     * @return A fully initialized {@link Texture}
     * 
     * @throws IOException If image loading fails
     */
    public static Texture load(String name, int target, int internalFormat, int format) throws IOException
    {
        /* Only support standard 8-bit RGB or RGBA internal formats */
        if (internalFormat != GL_RGBA8 && internalFormat != GL_RGB8) 
        {
            throw new IllegalArgumentException("Unsupported internal format: " + internalFormat);
        }


        /* Texture file path */
        final String path = TEXTURE_FOLDER + name;


        /* Texture dimensions */
        final int width, height;


        /* Texture file data */
        final ByteBuffer data;


        try (MemoryStack stack = MemoryStack.stackPush()) 
        {
            /* Prepare buffers to receive image dimensions and channel count */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            data = stbi_load(path, w, h, channels, 
                    internalFormat == GL_RGBA8 ? 4 : 
                    internalFormat == GL_RGB8  ? 3 :
                    internalFormat == GL_R8    ? 1 : 0);


            /* Check for failure */
            if (data == null) 
            {
                throw new IOException("Failed to load image '" + path + "': " + stbi_failure_reason());
            }


            /* Retrieve width and height from buffers */
            width = w.get();
            height = h.get();


            /* Create texture and upload image data to GPU */
            Texture texture = new Texture(width, height, target, internalFormat, format);
            texture.bind();
            texture.upload(data);
            texture.unbind();


            /* Free the loaded image data (CPU-side) */
            stbi_image_free(data);


            return texture;
        }
    }
}
