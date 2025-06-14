package com.lurch.texture;


import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


/**
 * TextureLoader handles loading texture files into OpenGL textures.
 */
public class TextureLoader
{
    private static final String TEXTURE_FOLDER = "lurch/src/main/resource/texture/";

    private static final TextureCache CACHE = new TextureCache();

    /**
     * Loads a texture using the given path and configuration.
     *
     * @param path   The relative path to the texture (relative to TEXTURE_FOLDER).
     * @param config The configuration for how the texture should be loaded and treated.
     * @return A Texture instance representing the loaded OpenGL texture.
     */
    public static Texture load(String path, TextureConfig config)
    {
        if (CACHE.contains(path, config)) {
            return CACHE.get(path, config);
        }

        int width, height;
        ByteBuffer image;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(false);
            image = STBImage.stbi_load(TEXTURE_FOLDER + path, w, h, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load texture '" + path + "': " +
                        STBImage.stbi_failure_reason());
            }

            width = w.get(0);
            height = h.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Texture loading failed for: " + path, e);
        }

        int textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, config.wrapS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, config.wrapT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, config.minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, config.magFilter);

        if (config.blend) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                     GL_RGBA, GL_UNSIGNED_BYTE, image);

        if (config.mipmaps) {
            glGenerateMipmap(GL_TEXTURE_2D);
        }

        STBImage.stbi_image_free(image);

        Texture texture = new Texture(textureID, width, height);
        CACHE.put(path, config, texture);
        return texture;
    }

    /**
     * Loads a texture using the default texture configuration.
     *
     * @param path The relative path to the texture.
     * @return The loaded Texture.
     */
    public static Texture load(String path) {
        return load(path, TextureConfig.DEFAULT);
    }

    /**
     * Removes a specific texture from the cache and cleans it up.
     */
    public static void remove(String path, TextureConfig config) {
        CACHE.remove(path, config);
    }

    /**
     * Clears the entire texture cache and frees OpenGL resources.
     */
    public static void cleanup() {
        CACHE.clear();
    }
}
