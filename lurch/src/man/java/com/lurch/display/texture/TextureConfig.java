package com.lurch.display.texture;


import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


/**
 * TextureConfig class for configuring texture parameters in OpenGL.
 */
public class TextureConfig 
{
    // Whether to use mipmaps for the texture
    private final boolean mipmaps;


    // The wrapping mode for the S coordinate
    private final int wrapS;

    // The wrapping mode for the T coordinate
    private final int wrapT;

    // The minification filter
    private final int minFilter;

    // The magnification filter
    private final int magFilter;


    /**
     * Texture target type.
     * This is typically GL_TEXTURE_2D for 2D textures.
     */
    public final int target;


    /**
     * Internal format of the texture.
     * This defines how the texture data is stored in memory.
     */
    public final int internalFormat;


    /**
     * Format of the texture data.
     * This defines how the pixel data is interpreted.
     */
    public final int format;


    /**
     * Number of color channles in the texture
     * This is passed to STB for decoding
     */
    public final int channels;



    public static final TextureConfig DEFAULT = new TextureConfig
    (
        false,
        GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE,
        GL_LINEAR, GL_LINEAR,
        GL_TEXTURE_2D, GL_RGBA8, GL_RGBA, 4
    );


    public static final TextureConfig PIXEL_ART = new TextureConfig
    (
        false,
        GL_NEAREST, GL_NEAREST,
        GL_NEAREST, GL_NEAREST,
        GL_TEXTURE_2D, GL_RGBA8, GL_RGBA, 4
    );


    public static final TextureConfig TILED_REPEAT = new TextureConfig
    (
        false,
        GL_REPEAT, GL_REPEAT,
        GL_LINEAR, GL_LINEAR,
        GL_TEXTURE_2D, GL_RGBA8, GL_RGBA, 4
    );


    public static final TextureConfig MIPMAPPED = new TextureConfig
    (
        true,
        GL_REPEAT, GL_REPEAT,
        GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR,
        GL_TEXTURE_2D, GL_RGBA8, GL_RGBA, 4
    );


    public static final TextureConfig OPAQUE_RGB = new TextureConfig
    (
        false,
        GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE,
        GL_LINEAR, GL_LINEAR,
        GL_TEXTURE_2D, GL_RGB8, GL_RGB, 3
    );


    // --- Constructors --- //


    /**
     * Constructor for Texture
     */
    public TextureConfig(
        boolean mipmaps,
        int wrapS, int wrapT,
        int minFilter, int magFilter,
        int target,
        int internalFormat,
        int format,
        int channels
    ) {
        this.mipmaps = mipmaps;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.target = target;
        this.internalFormat = internalFormat;
        this.format = format;
        this.channels = channels;
    }


    public void apply()
    {
        if (mipmaps) 
        {
            glGenerateMipmap(target);
        }

        glTexParameteri(target, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(target, GL_TEXTURE_MAG_FILTER, magFilter);
        glTexParameteri(target, GL_TEXTURE_WRAP_S, wrapS);
        glTexParameteri(target, GL_TEXTURE_WRAP_T, wrapT);
    }


    // --- Methods --- //


    /**
     * Generates a cache key for the texture configuration.
     * 
     * @param path The path to the texture file.
     * @return The cache key.
     */
    public String cacheKey(String path) {
        return path + "_" + mipmaps + "_" + wrapS + "_" + wrapT + "_" +
               minFilter + "_" + magFilter + "_" + internalFormat + "_" +
               format + "_" + channels + "_" + target;
    }
}
