package com.lurch.display.shader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*; // for GEOMETRY_SHADER
import static org.lwjgl.opengl.GL40.*; // for TESSELLATION
import static org.lwjgl.opengl.GL43.*; // for COMPUTE_SHADER

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for representing OpenGL shader types with extension-based inference.
 */
public enum ShaderType 
{
    /**
     * Enum constants for different shader types, each with a file extension and OpenGL type.
     * The extensions are used to identify the shader type based on the file name.
     */
    VERTEX          ("vert", GL_VERTEX_SHADER),
    FRAGMENT        ("frag", GL_FRAGMENT_SHADER),
    GEOMETRY        ("geom", GL_GEOMETRY_SHADER),
    TESS_CONTROL    ("tesc", GL_TESS_CONTROL_SHADER),
    TESS_EVALUATION ("tese", GL_TESS_EVALUATION_SHADER),
    COMPUTE         ("comp", GL_COMPUTE_SHADER);


    /**
     * Map to associate shader extensions with their corresponding ShaderType.
     * This allows for easy lookup based on file extension.
     */
    private static final Map<String, ShaderType> extensionMap = new HashMap<>();


    /**
     * Static block to populate the extensionMap with all ShaderType values.
     * This is done once when the class is loaded, allowing for efficient lookups.
     */
    static 
    {
        for (ShaderType type : values()) 
        {
            extensionMap.put(type.extension, type);
        }
    }


    /**
     * File extension associated with the shader type.
     * This is used to identify the shader type based on the file name.
     */
    private final String extension;


    /**
     * OpenGL type constant for the shader.
     * This is used when compiling the shader in OpenGL.
     */
    private final int glType;
  

    /**
     * Constructor for ShaderType enum.
     * Initializes the extension and OpenGL type for the shader.
     *
     * @param extension the file extension associated with the shader type
     * @param glType    the OpenGL type constant for the shader
     */
    ShaderType(String extension, int glType) 
    {
        this.extension = extension;
        this.glType = glType;
    }
  

    /**
     * Returns the OpenGL type constant for the shader.
     * This is used when compiling the shader in OpenGL.
     *
     * @return the OpenGL type constant for this shader type
     */
    public int getGLType() 
    {
        return glType;
    }


    /**
     * Returns the file extension associated with the shader type.
     * This is used to identify the shader type based on the file name.
     *
     * @return the file extension for this shader type
     */
    public String getExtension() 
    {
        return extension;
    }


    /**
     * Returns the ShaderType corresponding to the given file extension.
     * If the extension is not recognized, an IllegalArgumentException is thrown.
     *
     * @param ext the file extension to look up
     * @return the ShaderType corresponding to the given extension
     * @throws IllegalArgumentException if the extension is not recognized
     */
    public static ShaderType fromExtension(String ext) 
    {
        /* Retreive shader extension */
        ShaderType type = extensionMap.get(ext);


        /* Validate shader extension */
        if (type == null) 
        {
            throw new IllegalArgumentException("Unknown shader extension: " + ext);
        }
        

        return type;
    }
}
