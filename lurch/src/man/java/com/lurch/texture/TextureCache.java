package com.lurch.texture;


import java.util.HashMap;
import java.util.Map;

/**
 * TextureCache is a simple cache for storing and retrieving textures.
 * It uses a combination of the texture path and its configuration to create a unique key for each texture.
 */
public class TextureCache 
{
    // Cache for storing textures
    // The key is a combination of the texture path and its configuration
    private final Map<String, Texture> cache = new HashMap<>();


    // --- Private Methods --- //
    

    /**
     * Build a unique key for the texture based on its path and configuration.
     * This key is used to store and retrieve the texture from the cache.
     * 
     * @param path The path to the texture
     * @param config The configuration of the texture
     * @return The unique key for the texture
     */
    private String buildKey(String path, TextureConfig config) 
    {
        return path + "::" + config.toString();
    }


    // --- Methods --- //


    /**
     * Put a texture in the cache.
     * 
     * @param path The path to the texture
     * @param config The configuration of the texture
     * @param texture The texture to be cached
     */
    public void put(String path, TextureConfig config, Texture texture) 
    {
        cache.put(buildKey(path, config), texture);
    }

    /**
     * Remove a texture from the cache.
     * 
     * @param path The path to the texture
     * @param config The configuration of the texture
     */
    public void remove(String path, TextureConfig config) 
    {
        Texture t = cache.remove(buildKey(path, config));
        if (t != null) t.delete();
    }

    /**
     * Clear the cache.
     * This will remove all textures from the cache and clean them up.
     */
    public void clear() 
    {
        cache.values().forEach(Texture::delete);
        cache.clear();
    }


    // --- Getters --- //


    /** 
     * Get a texture from the cache
     * 
     * @param path The path to the texture
     * @param config The configuration of the texture
     * @return The texture if it exists in the cache, null otherwise
     */
    public Texture get(String path, TextureConfig config) 
    {
        return cache.get(buildKey(path, config));
    }


    // --- Checkers --- //


    /**
     * Check if a texture exists in the cache
     * 
     * @param path The path to the texture
     * @param config The configuration of the texture
     * @return True if the texture exists in the cache, false otherwise
     */
    public boolean contains(String path, TextureConfig config) 
    {
        return cache.containsKey(buildKey(path, config));
    }
}
