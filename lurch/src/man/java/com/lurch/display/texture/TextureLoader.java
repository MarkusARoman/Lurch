package com.lurch.display.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.stb.STBImage.*;

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
     */
    public static Texture load(String name)
    {
        return load(name, TextureConfig.DEFAULT);
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
     */
    public static Texture load(String name, TextureConfig config)
    {
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
            

            /* Load image data using STBImage */
            data = stbi_load(path, w, h, channels, config.channels);


            /* Check for failure */
            if (data == null) 
            {
                throw new IllegalArgumentException("Failed to load image '" + path + "': " + stbi_failure_reason());
            }


            /* Retrieve width and height from buffers */
            width = w.get();
            height = h.get();


            /* Create texture and upload image data to GPU */
            Texture texture = new Texture(width, height, config.target, config.internalFormat, config.format);

            
            texture.bind();         // Bind texture
            texture.upload(data);   // Upload texture data
            config.apply();         // Apply texture parameters
            texture.unbind();       // Unbind texture

            
            /* Free the loaded image data (CPU-side) */
            stbi_image_free(data);


            return texture;
        }
    }
}
