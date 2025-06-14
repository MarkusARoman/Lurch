package com.lurch.texture;


import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;

import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;


/**
 * TextureConfig class for configuring texture parameters in OpenGL.
 * This class encapsulates various texture settings such as mipmaps, blending,
 * wrapping modes, and filtering modes.
 */
public class TextureConfig 
{
    // Whether to use mipmaps for the texture
    public final boolean mipmaps;

    // Whether to use blending for the texture
    public final boolean blend;


    // The wrapping mode for the S coordinate
    public final int wrapS;

    // The wrapping mode for the T coordinate
    public final int wrapT;

    // The minification filter
    public final int minFilter;

    // The magnification filter
    public final int magFilter;


    // Preset: Default
    public static final TextureConfig DEFAULT = new TextureConfig(
        false, true, GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE, GL_LINEAR, GL_LINEAR
    );

    // Preset: Pixel-perfect
    public static final TextureConfig PIXEL_ART = new TextureConfig(
        false, true, GL_NEAREST, GL_NEAREST, GL_NEAREST, GL_NEAREST
    );

    // Preset: Pixel-perfect
    public static final TextureConfig OPAQUE_PIXEL_ART = new TextureConfig(
        false, false, GL_NEAREST, GL_NEAREST, GL_NEAREST, GL_NEAREST
    );

    // Preset: No blending (for opaque UI/objects)
    public static final TextureConfig OPAQUE = new TextureConfig(
        false, false, GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE, GL_LINEAR, GL_LINEAR
    );


    // --- Constructors --- //


    /**
     * Constructor for TextureConfig.
     * 
     * @param mipmaps   Whether to use mipmaps.
     * @param wrapS     The wrapping mode for the S coordinate.
     * @param wrapT     The wrapping mode for the T coordinate.
     * @param minFilter The minification filter.
     * @param magFilter The magnification filter.
     */
    public TextureConfig(boolean mipmaps, boolean blend, int wrapS, int wrapT, int minFilter, int magFilter) 
    {
        this.mipmaps = mipmaps;
        this.blend = blend;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
    }


    // --- Methods --- //


    /**
     * Generates a cache key for the texture configuration.
     * 
     * @param path The path to the texture file.
     * @return The cache key.
     */
    public String cacheKey(String path)
    {
        return path + "_" + mipmaps  + "_" + wrapS + "_" + wrapT + "_" + minFilter + "_" + magFilter;
    }
}
